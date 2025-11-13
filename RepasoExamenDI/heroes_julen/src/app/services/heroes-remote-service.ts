import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable, switchMap, take } from 'rxjs';
import { Hero } from '../hero/hero';

@Injectable({
  providedIn: 'root'
})
export class HeroesRemoteService {
  private http = inject(HttpClient);

  private apiUrl = 'http://localhost:3000/heroes';

  getAllHeroes(): Observable<Hero[]> {
    return this.http.get<Hero[]>(this.apiUrl);
  }

  getFeaturedHeroes(limit = 4): Observable<Hero[]> {
    return this.http.get<Hero[]>(`${this.apiUrl}?_limit=${limit}`);
  }

  getHeroById(id: number): Observable<Hero> {
    return this.http.get<Hero>(`${this.apiUrl}/${id}`);
  }

  createHero(name: string, power?: string): Observable<Hero> {
    const payload: Partial<Hero> = { name };
    if (power) payload.power = power;

    return this.getAllHeroes().pipe(
      take(1),
      switchMap((heroes) => {
        const maxId = heroes.reduce((m, h) => {
          const idNum = Number(h.id ?? 0);
          return Number.isFinite(idNum) ? Math.max(m, idNum) : m;
        }, 0);
        const newId = maxId + 1;
        const body = { ...payload, id: newId } as Partial<Hero>;
        return this.http.post<Hero>(this.apiUrl, body);
      })
    );
  }

  updateHero(id: number, changes: Partial<Hero>): Observable<Hero> {
    return this.http.patch<Hero>(`${this.apiUrl}/${id}`, changes);
  }

  deleteHero(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}