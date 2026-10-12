import { Component, OnDestroy } from "@angular/core"

@Component({
  selector: "app-home",
  templateUrl: "./home.component.html",
  styleUrls: ["./home.component.scss"],
})
export class HomeComponent implements OnDestroy {
  heroImages: string[] = [
    "https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?auto=format&fit=crop&w=1920&q=85",
    "https://images.unsplash.com/photo-1555396273-367ea4eb4db5?auto=format&fit=crop&w=1920&q=85",
    "https://images.unsplash.com/photo-1414235077428-338989a2e8c0?auto=format&fit=crop&w=1920&q=85",
    "https://images.unsplash.com/photo-1514933651103-005eec06c04b?auto=format&fit=crop&w=1920&q=85",
  ]

  activeSlide = 0

  private carouselTimer: ReturnType<typeof setInterval>

  constructor() {
    this.carouselTimer = setInterval(() => {
      this.activeSlide = (this.activeSlide + 1) % this.heroImages.length
    }, 5000)
  }

  ngOnDestroy(): void {
    clearInterval(this.carouselTimer)
  }
}
