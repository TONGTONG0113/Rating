import { Component, OnInit } from "@angular/core"
import { HttpClient } from "@angular/common/http"

interface RestaurantRanking {
  restaurantId: number
  restaurantName: string
  averageRating: number
  reviewCount: number
}

@Component({
  selector: "app-restaurant-ranking",
  templateUrl: "./restaurant-ranking.component.html",
  styleUrls: ["./restaurant-ranking.component.scss"],
})
export class RestaurantRankingComponent implements OnInit {
  rankings: RestaurantRanking[] = []
  loading = true
  error = false

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.http.get<RestaurantRanking[]>("http://localhost:8080/api/restaurants/ranking").subscribe({
      next: (data) => {
        console.log("Restaurant ranking data:", data)
        this.rankings = data
        this.loading = false
      },
      error: (err) => {
        console.error("Failed to load restaurant ranking", err)
        this.error = true
        this.loading = false
      },
    })
  }
}
