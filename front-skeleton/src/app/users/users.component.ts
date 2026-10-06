import { CommonModule } from "@angular/common"
import { Component, OnInit } from "@angular/core"
import { FormsModule } from "@angular/forms"

import { User } from "../models/user.model"
import { Review } from "../models/review.model"

import { UserService } from "../services/user.service"
import { ReviewService } from "../services/review.service"

type SortField = "id" | "name" | "email" | "createdAt"

type StatusFilter = "all" | "active" | "disabled"

@Component({
  selector: "app-users",
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: "./users.component.html",
  styleUrls: ["./users.component.scss"],
})
export class UsersComponent implements OnInit {
  users: User[] = []

  formUser: User = {
    name: "",
    email: "",
  }

  editingUserId?: number

  selectedUser?: User

  selectedUserReviews: Review[] = []

  reviewsLoading = false

  reviewErrorMessage = ""

  searchTerm = ""

  statusFilter: StatusFilter = "all"

  sortField: SortField = "id"

  sortAscending = true

  currentPage = 1

  pageSize = 5

  readonly pageSizes = [5, 10, 20]

  loading = false

  errorMessage = ""

  successMessage = ""

  constructor(private userService: UserService, private reviewService: ReviewService) {}

  ngOnInit(): void {
    this.loadUsers()
  }

  get filteredUsers(): User[] {
    const search = this.searchTerm.trim().toLowerCase()

    return this.users.filter((user) => {
      const matchesSearch =
        !search ||
        (user.id?.toString() ?? "").includes(search) ||
        user.name.toLowerCase().includes(search) ||
        user.email.toLowerCase().includes(search)

      const matchesStatus =
        this.statusFilter === "all" ||
        (this.statusFilter === "active" && user.active !== false) ||
        (this.statusFilter === "disabled" && user.active === false)

      return matchesSearch && matchesStatus
    })
  }

  get paginatedUsers(): User[] {
    const start = (this.currentPage - 1) * this.pageSize

    return this.filteredUsers.slice(start, start + this.pageSize)
  }

  get totalPages(): number {
    return Math.max(1, Math.ceil(this.filteredUsers.length / this.pageSize))
  }

  get activeUsersCount(): number {
    return this.users.filter((user) => user.active !== false).length
  }

  get disabledUsersCount(): number {
    return this.users.filter((user) => user.active === false).length
  }

  get rangeStart(): number {
    if (this.filteredUsers.length === 0) {
      return 0
    }

    return (this.currentPage - 1) * this.pageSize + 1
  }

  get rangeEnd(): number {
    return Math.min(this.currentPage * this.pageSize, this.filteredUsers.length)
  }

  loadUsers(): void {
    this.loading = true

    this.userService.getAll().subscribe({
      next: (users) => {
        this.users = users

        this.sortUsers()

        if (this.currentPage > this.totalPages) {
          this.currentPage = this.totalPages
        }

        if (this.selectedUser?.id) {
          this.selectedUser = this.users.find((user) => user.id === this.selectedUser?.id)
        }

        this.loading = false
      },

      error: () => {
        this.errorMessage = "Unable to load users."

        this.loading = false
      },
    })
  }

  refreshUsers(): void {
    this.clearMessages()

    this.loadUsers()

    if (this.selectedUser?.id !== undefined) {
      this.loadUserReviews(this.selectedUser.id)
    }
  }

  resetFilters(): void {
    this.searchTerm = ""

    this.statusFilter = "all"

    this.sortField = "id"

    this.sortAscending = true

    this.pageSize = 5

    this.currentPage = 1

    this.sortUsers()
  }

  onFiltersChanged(): void {
    this.currentPage = 1
  }

  toggleSort(field: SortField): void {
    if (this.sortField === field) {
      this.sortAscending = !this.sortAscending
    } else {
      this.sortField = field
      this.sortAscending = true
    }

    this.currentPage = 1

    this.sortUsers()
  }

  sortUsers(): void {
    this.users.sort((a, b) => {
      let result = 0

      if (this.sortField === "id") {
        result = (a.id ?? 0) - (b.id ?? 0)
      } else if (this.sortField === "name") {
        result = a.name.localeCompare(b.name, undefined, {
          sensitivity: "base",
        })
      } else if (this.sortField === "email") {
        result = a.email.localeCompare(b.email, undefined, {
          sensitivity: "base",
        })
      } else if (this.sortField === "createdAt") {
        const dateA = new Date(a.createdAt ?? 0).getTime()

        const dateB = new Date(b.createdAt ?? 0).getTime()

        result = dateA - dateB
      }

      return this.sortAscending ? result : -result
    })
  }

  changePageSize(): void {
    this.currentPage = 1
  }

  previousPage(): void {
    if (this.currentPage > 1) {
      this.currentPage--
    }
  }

  nextPage(): void {
    if (this.currentPage < this.totalPages) {
      this.currentPage++
    }
  }

  saveUser(): void {
    this.clearMessages()

    if (!this.formUser.name.trim() || !this.formUser.email.trim()) {
      this.errorMessage = "Name and email are required."

      return
    }

    if (this.editingUserId !== undefined) {
      this.updateUser()
    } else {
      this.createUser()
    }
  }

  createUser(): void {
    this.userService.create(this.formUser).subscribe({
      next: () => {
        this.successMessage = "User created successfully."

        this.resetForm(false)

        this.loadUsers()
      },

      error: (error) => {
        if (error.status === 409) {
          this.errorMessage = "This email is already used."
        } else if (error.status === 400) {
          this.errorMessage = "Invalid user information."
        } else {
          this.errorMessage = "Unable to create user."
        }
      },
    })
  }

  startEdit(user: User): void {
    if (user.id === undefined) {
      return
    }

    this.clearMessages()

    this.editingUserId = user.id

    this.formUser = {
      name: user.name,
      email: user.email,
    }

    window.scrollTo({
      top: 0,
      behavior: "smooth",
    })
  }

  updateUser(): void {
    if (this.editingUserId === undefined) {
      return
    }

    this.userService.update(this.editingUserId, this.formUser).subscribe({
      next: () => {
        this.successMessage = "User updated successfully."

        this.resetForm(false)

        this.loadUsers()
      },

      error: (error) => {
        if (error.status === 409) {
          this.errorMessage = "This email is already used."
        } else if (error.status === 404) {
          this.errorMessage = "User not found."
        } else {
          this.errorMessage = "Unable to update user."
        }
      },
    })
  }

  toggleUserStatus(user: User): void {
    if (user.id === undefined) {
      return
    }

    const newStatus = user.active === false

    const action = newStatus ? "enable" : "disable"

    const confirmed = confirm(`Are you sure you want to ${action} ${user.name}?`)

    if (!confirmed) {
      return
    }

    this.clearMessages()

    this.userService.setActive(user.id, newStatus).subscribe({
      next: () => {
        this.successMessage = newStatus ? "User enabled successfully." : "User disabled successfully."

        this.loadUsers()
      },

      error: () => {
        this.errorMessage = "Unable to change user status."
      },
    })
  }

  deleteUser(user: User): void {
    if (user.id === undefined) {
      return
    }

    const confirmed = confirm(`Delete ${user.name}? Their reviews will also be deleted.`)

    if (!confirmed) {
      return
    }

    this.clearMessages()

    this.userService.delete(user.id).subscribe({
      next: () => {
        this.successMessage = "User deleted successfully."

        if (this.editingUserId === user.id) {
          this.resetForm(false)
        }

        if (this.selectedUser?.id === user.id) {
          this.closeDetails()
        }

        this.loadUsers()
      },

      error: () => {
        this.errorMessage = "Unable to delete user."
      },
    })
  }

  viewUser(user: User): void {
    this.selectedUser = user

    this.selectedUserReviews = []

    this.reviewErrorMessage = ""

    if (user.id !== undefined) {
      this.loadUserReviews(user.id)
    }
  }

  loadUserReviews(userId: number): void {
    this.reviewsLoading = true

    this.reviewErrorMessage = ""

    this.reviewService.getByUser(userId).subscribe({
      next: (reviews) => {
        this.selectedUserReviews = reviews

        this.reviewsLoading = false
      },

      error: () => {
        this.reviewErrorMessage = "Unable to load this user's reviews."

        this.reviewsLoading = false
      },
    })
  }

  deleteReview(review: Review): void {
    if (review.id === undefined) {
      return
    }

    const confirmed = confirm(`Delete this review for ${review.restaurantTitle ?? "this restaurant"}?`)

    if (!confirmed) {
      return
    }

    this.reviewService.delete(review.id).subscribe({
      next: () => {
        this.successMessage = "Review deleted successfully."

        this.selectedUserReviews = this.selectedUserReviews.filter((item) => item.id !== review.id)
      },

      error: () => {
        this.reviewErrorMessage = "Unable to delete review."
      },
    })
  }

  closeDetails(): void {
    this.selectedUser = undefined

    this.selectedUserReviews = []

    this.reviewErrorMessage = ""
  }

  copyId(user: User): void {
    if (user.id === undefined) {
      return
    }

    this.copyText(user.id.toString(), "ID")
  }

  copyEmail(user: User): void {
    this.copyText(user.email, "Email")
  }

  private copyText(value: string, label: string): void {
    navigator.clipboard
      .writeText(value)
      .then(() => {
        this.successMessage = `${label} copied.`

        this.errorMessage = ""
      })
      .catch(() => {
        this.errorMessage = `Unable to copy ${label}.`
      })
  }

  cancelEdit(): void {
    this.resetForm()
  }

  resetForm(clearMessages: boolean = true): void {
    this.formUser = {
      name: "",
      email: "",
    }

    this.editingUserId = undefined

    if (clearMessages) {
      this.clearMessages()
    }
  }

  clearMessages(): void {
    this.errorMessage = ""
    this.successMessage = ""
  }
}
