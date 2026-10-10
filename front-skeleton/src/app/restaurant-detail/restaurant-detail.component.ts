import { Component, OnInit } from "@angular/core"
import { ActivatedRoute } from "@angular/router"

import { ReviewService } from "../services/review.service"
import { Review } from "../models/review.model"

interface Restaurant {
  id: number
  name: string
  category: string
  price: string
  rating: number
  reviews: number
  address: string
  description: string
  image: string
}

@Component({
  selector: "app-restaurant-detail",
  templateUrl: "./restaurant-detail.component.html",
  styleUrls: ["./restaurant-detail.component.scss"],
})
export class RestaurantDetailComponent implements OnInit {
  restaurantId!: number

  restaurant?: Restaurant

  reviews: Review[] = []
  reviewsLoading = false
  reviewsError = ""

  reviewRating = 5
  reviewSummary = ""
  reviewDetails = ""

  // Temporary test account. Replace with the selected/logged-in user later.
  reviewUserId = 1

  showReviewForm = false
  submittingReview = false
  submitMessage = ""
  submitMessageIsError = false

  restaurants: Restaurant[] = [
    {
      id: 1,
      name: "Le Petit Paris",
      category: "Français",
      price: "€€",
      rating: 4.8,
      reviews: 126,
      address: "Paris 5e",
      description: "Cuisine française traditionnelle et produits frais.",
      image: "https://images.unsplash.com/photo-1515003197210-e0cd71810b5f?auto=format&fit=crop&w=1200&q=80",
    },
    {
      id: 2,
      name: "Tokyo House",
      category: "Japonais",
      price: "€€",
      rating: 4.6,
      reviews: 98,
      address: "Paris 8e",
      description: "Cuisine japonaise authentique et raffinée.",
      image: "https://images.unsplash.com/photo-1579871494447-9811cf80d66c?auto=format&fit=crop&w=1200&q=80",
    },
    {
      id: 3,
      name: "Bella Italia",
      category: "Italien",
      price: "€€",
      rating: 4.7,
      reviews: 154,
      address: "Paris 11e",
      description: "Pizzas et spécialités italiennes faites maison.",
      image: "https://images.unsplash.com/photo-1579684947550-22e945225d9a?auto=format&fit=crop&w=1200&q=80",
    },
    {
      id: 4,
      name: "Dragon d'Or",
      category: "Chinois",
      price: "€€",
      rating: 4.5,
      reviews: 87,
      address: "Paris 13e",
      description: "Une cuisine chinoise traditionnelle et généreuse.",
      image: "https://images.unsplash.com/photo-1563245372-f21724e3856d?auto=format&fit=crop&w=1200&q=80",
    },
    {
      id: 5,
      name: "Bombay Palace",
      category: "Indien",
      price: "€€€",
      rating: 4.4,
      reviews: 76,
      address: "Paris 10e",
      description: "Saveurs indiennes authentiques et épices traditionnelles.",
      image: "https://images.unsplash.com/photo-1585937421612-70a008356fbe?auto=format&fit=crop&w=1200&q=80",
    },
    {
      id: 6,
      name: "Burger Factory",
      category: "Fast Food",
      price: "€",
      rating: 4.2,
      reviews: 203,
      address: "Paris 1er",
      description: "Burgers gourmands préparés avec des produits frais.",
      image: "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?auto=format&fit=crop&w=1200&q=80",
    },
  ]

  constructor(private route: ActivatedRoute, private reviewService: ReviewService) {}

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.restaurantId = Number(params["id"])

      const foundRestaurant = this.restaurants.find((item) => item.id === this.restaurantId)

      if (!foundRestaurant) {
        this.restaurant = undefined
        this.reviews = []
        this.reviewsError = "Restaurant not found."
        return
      }

      this.restaurant = foundRestaurant
      this.loadReviews()
    })
  }

  private loadReviews(): void {
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

    if (!this.restaurantId || !this.restaurant) {
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
