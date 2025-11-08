import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeroesRemoteService } from '../../../server/json-server';
import { Hero } from '../../hero/hero';

@Component({
  selector: 'app-leer-server',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './leer-server.html',
  styleUrls: ['./leer-server.css'],
})
export class LeerServer {
  numero: number = 1;
  heroes: Hero[] = [];
  loading = false;
  error: string | null = null;

  constructor(private remote: HeroesRemoteService) {}

  vertodos(): void {
    this.loading = true;
    this.error = null;
    this.remote.getAllHeroes().subscribe({
      next: (h) => (this.heroes = h),
      error: (err) => {
        console.error(err);
        this.error = 'Error al leer los héroes desde el servidor';
        this.loading = false;
      },
      complete: () => {
        this.loading = false;
      }
    });
  }

  verPorId(): void {
    const input = document.getElementById('numeroHeroe') as HTMLInputElement | null;
    if (!input) {
      this.error = 'Elemento de entrada no encontrado';
      return;
    }
    const num = Number(input.value);
    if (Number.isNaN(num)) {
      this.error = 'Número inválido';
      return;
    }

    this.loading = true;
    this.error = null;
    this.remote.getHeroById(num).subscribe({
      next: (h) => {
        this.heroes = [h];
      },
      error: (err) => {
        console.error(err);
        this.error = 'Error al leer el héroe desde el servidor';
        this.loading = false;
      },
      complete: () => {
        this.loading = false;
      }
    });
  }
}
