import { Component, ChangeDetectorRef, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { Hero } from '../../hero/hero';
import { HeroesRemoteService } from '../../services/heroes-remote-service';

@Component({
  selector: 'app-home-page',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './home-page.html',
  styleUrl: './home-page.css',
})
export class HomePage implements OnInit {
  topHeroes: Hero[] = [];

  constructor(private router: Router, private remote: HeroesRemoteService, private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.remote.getFeaturedHeroes(4).subscribe({
      next: (hs) => {
        this.topHeroes = hs;
        this.cdr.markForCheck();
      },
      error: (err) => console.error('Failed to load top heroes', err)
    });
  }

  goToHeroesPage() {
    this.router.navigate(['/heroes']);
  }
}
