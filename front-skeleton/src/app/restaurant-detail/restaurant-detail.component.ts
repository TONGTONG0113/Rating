import { Component, OnInit } from "@angular/core"
import { ActivatedRoute } from "@angular/router"
import { HttpClient } from "@angular/common/http"

interface Restaurant {
  id: number
  title: string
  address: string
  openingHours: string
  imageUrl: string | null
}

@Component({
  selector: "app-restaurant-detail",
  templateUrl: "./restaurant-detail.component.html",
  styleUrls: ["./restaurant-detail.component.scss"],
})
export class RestaurantDetailComponent implements OnInit {
  restaurant?: Restaurant
  loading = true
  errorMessage = ""

  constructor(private route: ActivatedRoute, private http: HttpClient) {}

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      const id = Number(params["id"])

      this.loading = true
      this.restaurant = undefined
      this.errorMessage = ""

      this.http.get<Restaurant>(`http://localhost:8080/api/restaurants/${id}`).subscribe({
        next: (data) => {
          this.restaurant = data
          this.loading = false
        },
        error: (error) => {
          console.error(error)
          this.errorMessage = "Impossible de charger ce restaurant."
          this.loading = false
        },
      })
    })
  }
}
