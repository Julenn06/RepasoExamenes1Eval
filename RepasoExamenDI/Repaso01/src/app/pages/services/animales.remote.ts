import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable, switchMap, take } from 'rxjs';
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
    return this.http.get<Animal>(`${this.apiUrl}/${id}`);
  }

  createAnimal(nombre: string, tipo?: string): Observable<Animal> {
    const payload: Partial<Animal> = { nombre };
    if (tipo) payload.tipo = tipo;

    return this.getAllAnimales().pipe(
      take(1),
      switchMap((Animales) => {
        // Calcular el siguiente ID como número
        const maxId = Animales.reduce((m, h) => {
          const idNum = typeof h.id === 'string' ? parseInt(h.id, 10) : h.id;
          return Number.isFinite(idNum) ? Math.max(m, idNum) : m;
        }, 0);
        const newId = maxId + 1;
        
        // Asegurarnos de que el ID es numérico
        const body = { ...payload, id: newId };
        return this.http.post<Animal>(this.apiUrl, body);
      })
    );
  }

  updateAnimal(id: number, changes: Partial<Animal>): Observable<Animal> {
    return this.http.patch<Animal>(`${this.apiUrl}/${id}`, changes);
  }

  deleteAnimal(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}