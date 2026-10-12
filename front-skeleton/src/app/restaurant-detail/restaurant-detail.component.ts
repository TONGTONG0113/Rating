import { Component, OnInit } from "@angular/core"
import { ActivatedRoute } from "@angular/router"
import { HttpClient } from "@angular/common/http"

import { ReviewService } from "../services/review.service"
import { Review } from "../models/review.model"

interface Restaurant {
  id: number
  title: string
  address: string
  openingHours: string
  imageUrl: string | null

  // Optional fields for additional detail-page information.
  // The backend may not provide these fields.
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

  reviewRating = 5
  reviewSummary = ""
  reviewDetails = ""

  // Temporary test account. Replace with the logged-in user later.
  reviewUserId = 1

  showReviewForm = false
  submittingReview = false
  submitMessage = ""
  submitMessageIsError = false

  // Local UI state only; this does not persist favorites to the backend.
  isFavorite = false

  constructor(private route: ActivatedRoute, private http: HttpClient, private reviewService: ReviewService) {}

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      const id = Number(params["id"])

      this.restaurantId = id
      this.restaurant = undefined
      this.reviews = []
      this.errorMessage = ""
      this.reviewsError = ""
      this.submitMessage = ""
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
  }

  cancelReviewForm(): void {
    this.showReviewForm = false
    this.submitMessage = ""
    this.submitMessageIsError = false
  }

  submitReview(): void {
    const summary = this.reviewSummary.trim()
    const details = this.reviewDetails.trim()

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
      userId: this.reviewUserId,
      restaurantId: this.restaurantId,
    }

    this.reviewService.create(newReview).subscribe({
      next: (createdReview) => {
        this.reviews = [createdReview, ...this.reviews]

        this.reviewSummary = ""
        this.reviewDetails = ""
        this.reviewRating = 5

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
