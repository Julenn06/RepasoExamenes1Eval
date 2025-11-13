import {
  Component,
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  OnInit,
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Hero } from '../../hero/hero';
import { HeroesRemoteService } from '../../services/heroes-remote-service';

@Component({
  selector: 'app-heroes-page',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, HttpClientModule],
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './heroes-page.html',
  styleUrl: './heroes-page.css',
})
export class HeroesPage implements OnInit {
  allHeroes: Hero[] = [];

  showAddHero = false;
  newHeroName = '';
  newHeroPower = '';

  constructor(private cdr: ChangeDetectorRef, private remote: HeroesRemoteService) {}

  ngOnInit(): void {
    this.loadHeroes();
  }

  private loadHeroes() {
    this.remote.getAllHeroes().subscribe({
      next: (hs) => {
        this.allHeroes = hs;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error loading heroes', err);
      }
    });
  }

  deleteHero(heroId: number) {
    this.remote.deleteHero(heroId).subscribe({
      next: () => {
        this.loadHeroes();
      },
      error: (err) => console.error('Delete failed', err)
    });
  }

  submitAddHero() {
    const name = this.newHeroName.trim();
    const power = this.newHeroPower.trim();
    if (!name || !power) return;

    this.remote.createHero(name, power).subscribe({
      next: (created) => {
        this.loadHeroes();
        this.closeAddHero();
      },
      error: (err) => console.error('Create failed', err)
    });
  }

  closeAddHero() {
    this.showAddHero = false;
    this.newHeroName = '';
    this.newHeroPower = '';
  }

  trackById(_index: number, hero: { id: number }) {
    return hero.id;
  }
}
