import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { map, Observable, switchMap, take } from 'rxjs';
import { Animal } from '../animal/animal';

@Injectable({
  providedIn: 'root'
})
export class AnimalesRemoteService {

  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:3000/animales';

  getAllAnimales(): Observable<Animal[]> {
    return this.http.get<Animal[]>(this.apiUrl);
  }

  getFeaturedAnimales(limit = 4): Observable<Animal[]> {
    return this.http.get<Animal[]>(`${this.apiUrl}?_limit=${limit}`);
  }

  getAnimalById(id: number): Observable<Animal> {
    return this.http.get<Animal>(`${this.apiUrl}/${String(id)}`);
  }

  getAnimalByName(name: string): Observable<Animal> {
    return this.http.get<Animal[]>(`${this.apiUrl}?nombre=${encodeURIComponent(name)}`).pipe(
      map(animals => {
        return animals[0];
      })
    );
  }

  createAnimal(nombre: string, tipo?: string): Observable<Animal> {
    return this.getAllAnimales().pipe(
      take(1),
      switchMap((Animales) => {
        const maxId = Animales.reduce((m, h) => {
          const idNum = typeof h.id === 'string' ? parseInt(h.id, 10) : h.id;
          return Number.isFinite(idNum) ? Math.max(m, idNum) : m;
        }, 0);
        const newId = maxId + 1;

        const body = {
          id: String(newId),
          nombre: nombre,
          tipo: tipo || ''
        };
        return this.http.post<Animal>(this.apiUrl, body);
      })
    );
  }

  updateAnimal(id: number, changes: Partial<Animal>): Observable<Animal> {
    // PATCH solo envía los cambios, no el ID completo
    return this.http.patch<Animal>(`${this.apiUrl}/${String(id)}`, changes);
  }

  deleteAnimal(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${String(id)}`);
  }
}