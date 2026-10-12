import { Component, OnInit } from "@angular/core"
import { ActivatedRoute } from "@angular/router"
import { HttpClient } from "@angular/common/http"

import { ReviewService } from "../services/review.service"
import { UserService } from "../services/user.service"
import { Review } from "../models/review.model"
import { User } from "../models/user.model"

interface Restaurant {
  id: number
  title: string
  address: string
  openingHours: string
  imageUrl: string | null
  rating?: number
  description?: string
}

@Component({
  selector: "app-restaurant-detail",
  templateUrl: "./restaurant-detail.component.html",
  styleUrls: ["./restaurant-detail.component.scss"],
})
export class RestaurantDetailComponent implements OnInit {
  restaurant?: Restaurant
  restaurantId: number | null = null

  loading = true
  errorMessage = ""

  reviews: Review[] = []
  reviewsLoading = false
  reviewsError = ""

  users: User[] = []
  usersLoading = false
  usersError = ""

  reviewRating = 5
  reviewSummary = ""
  reviewDetails = ""

  reviewUserId: number | null = null
  reviewUserSearch = ""

  showReviewForm = false
  submittingReview = false
  submitMessage = ""
  submitMessageIsError = false

  // Local UI state only; favorites are not saved to the backend.
  isFavorite = false

  constructor(
    private route: ActivatedRoute,
    private http: HttpClient,
    private reviewService: ReviewService,
    private userService: UserService,
  ) {}

  get activeUsers(): User[] {
    return this.users.filter((user) => user.id !== undefined && user.active !== false)
  }

  ngOnInit(): void {
    this.loadUsers()

    this.route.params.subscribe((params) => {
      const id = Number(params["id"])

      this.restaurantId = id
      this.restaurant = undefined
      this.reviews = []
      this.errorMessage = ""
      this.reviewsError = ""
      this.submitMessage = ""
      this.showReviewForm = false
      this.loading = true

      if (!Number.isInteger(id) || id <= 0) {
        this.errorMessage = "Invalid restaurant ID."
        this.loading = false
        return
      }

      this.http.get<Restaurant>(`http://localhost:8080/api/restaurants/${id}`).subscribe({
        next: (data) => {
          this.restaurant = data
          this.loading = false
          this.loadReviews()
        },
        error: (error) => {
          console.error("Failed to load restaurant:", error)
          this.errorMessage = "Impossible de charger ce restaurant."
          this.loading = false
        },
      })
    })
  }

  private loadUsers(): void {
    this.usersLoading = true
    this.usersError = ""

    this.userService.getAll().subscribe({
      next: (users) => {
        this.users = users
        this.usersLoading = false

        if (this.reviewUserId !== null && !this.activeUsers.some((user) => user.id === this.reviewUserId)) {
          this.reviewUserId = null
          this.reviewUserSearch = ""
        }
      },
      error: (error) => {
        console.error("Failed to load users:", error)
        this.users = []
        this.usersLoading = false
        this.usersError = "Unable to load users. Please try again later."
      },
    })
  }

  private loadReviews(): void {
    if (this.restaurantId === null) {
      return
    }

    this.reviewsLoading = true
    this.reviewsError = ""

    this.reviewService.getByRestaurant(this.restaurantId).subscribe({
      next: (reviews) => {
        this.reviews = reviews
        this.reviewsLoading = false
      },
      error: (error) => {
        console.error("Failed to load reviews:", error)
        this.reviewsError = "Unable to load reviews. Please try again later."
        this.reviewsLoading = false
      },
    })
  }

  toggleFavorite(): void {
    this.isFavorite = !this.isFavorite
  }

  openReviewForm(): void {
    this.showReviewForm = true
    this.submitMessage = ""
    this.submitMessageIsError = false

    if (!this.usersLoading && this.users.length === 0) {
      this.loadUsers()
    }
  }

  cancelReviewForm(): void {
    this.showReviewForm = false
    this.submitMessage = ""
    this.submitMessageIsError = false
  }

  /**
   * Called whenever the user types in the searchable user input.
   * Only an exact match with a listed active user selects a user ID.
   */
  onReviewUserSearchChange(value: string): void {
    this.reviewUserSearch = value

    const selectedUser = this.activeUsers.find((user) => this.getUserOptionLabel(user) === value)

    this.reviewUserId = selectedUser?.id ?? null
  }

  getUserOptionLabel(user: User): string {
    return `${user.name} (ID: ${user.id})`
  }

  submitReview(): void {
    const summary = this.reviewSummary.trim()
    const details = this.reviewDetails.trim()

    if (this.reviewUserId === null) {
      this.submitMessage = "Please choose a valid user from the suggestions."
      this.submitMessageIsError = true
      return
    }

    const selectedUser = this.activeUsers.find((user) => user.id === this.reviewUserId)

    if (!selectedUser || selectedUser.id === undefined) {
      this.submitMessage = "Please select a valid, active user."
      this.submitMessageIsError = true
      this.reviewUserId = null
      this.reviewUserSearch = ""
      return
    }

    if (!summary) {
      this.submitMessage = "Please enter a review title."
      this.submitMessageIsError = true
      return
    }

    if (!Number.isInteger(this.reviewRating) || this.reviewRating < 1 || this.reviewRating > 5) {
      this.submitMessage = "Please select a rating between 1 and 5."
      this.submitMessageIsError = true
      return
    }

    if (this.restaurantId === null || !this.restaurant) {
      this.submitMessage = "Restaurant not found."
      this.submitMessageIsError = true
      return
    }

    this.submittingReview = true
    this.submitMessage = ""
    this.submitMessageIsError = false

    const newReview: Review = {
      rating: this.reviewRating,
      summary,
      details: details || undefined,
      userId: selectedUser.id,
      restaurantId: this.restaurantId,
    }

    this.reviewService.create(newReview).subscribe({
      next: (createdReview) => {
        const reviewToDisplay: Review = {
          ...createdReview,
          userId: createdReview.userId ?? selectedUser.id!,
          userName: createdReview.userName || selectedUser.name,
          restaurantId: createdReview.restaurantId ?? this.restaurantId!,
          restaurantTitle: createdReview.restaurantTitle || this.restaurant?.title,
        }

        this.reviews = [reviewToDisplay, ...this.reviews]

        this.reviewSummary = ""
        this.reviewDetails = ""
        this.reviewRating = 5
        this.reviewUserId = null
        this.reviewUserSearch = ""

        this.submittingReview = false
        this.showReviewForm = false
        this.submitMessage = "Your review was published successfully."
        this.submitMessageIsError = false
      },
      error: (error) => {
        console.error("Failed to submit review:", error)

        this.submittingReview = false
        this.submitMessageIsError = true

        if (error.status === 404) {
          this.submitMessage = "User or restaurant not found. Please check the IDs."
        } else if (error.status === 400) {
          this.submitMessage = "Invalid review data. Please check your rating and title."
        } else {
          this.submitMessage = "Unable to publish the review. Please try again."
        }
      },
    })
  }
}
