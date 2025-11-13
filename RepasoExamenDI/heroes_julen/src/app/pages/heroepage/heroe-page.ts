import { Component, ChangeDetectionStrategy, ChangeDetectorRef, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule, ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Hero } from '../../hero/hero';
import { HeroesRemoteService } from '../../services/heroes-remote-service';

@Component({
  selector: 'app-heroe-page',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, HttpClientModule],
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './heroe-page.html',
  styleUrl: './heroe-page.css',
})
export class HeroePage implements OnInit {
  hero: Hero | undefined;
  isEditing = false;
  editName = '';
  editPower = '';

  constructor(
    private route: ActivatedRoute,
    private remote: HeroesRemoteService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (!Number.isNaN(id)) {
      this.remote.getHeroById(id).subscribe({
        next: (h) => {
          this.hero = h;
          this.cdr.markForCheck();
        },
        error: (err) => console.error('Failed to load hero', err)
      });
    }
  }

  startEdit() {
    if (this.hero) {
      this.isEditing = true;
      this.editName = this.hero.name;
      this.editPower = this.hero.power;
    }
  }

  cancelEdit() {
    this.isEditing = false;
    this.editName = '';
    this.editPower = '';
  }

  saveEdit() {
    if (this.hero && this.editName.trim() && this.editPower.trim()) {
      const changes: Partial<Hero> = {
        name: this.editName.trim(),
        power: this.editPower.trim()
      };
      this.remote.updateHero(this.hero.id, changes).subscribe({
        next: (updated) => {
          this.hero = updated;
          this.isEditing = false;
          this.editName = '';
          this.editPower = '';
          this.cdr.markForCheck();
        },
        error: (err) => console.error('Update failed', err)
      });
    }
  }
}
