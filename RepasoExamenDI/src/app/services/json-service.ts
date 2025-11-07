import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class JsonService {
  private http = inject(HttpClient);

  getDb(): Observable<any> {
    return this.http.get('assets/db.json');
  }

  getDbById(id: number): Observable<any> {
    return this.http.get('assets/db.json').pipe(
      map((data: any) => {
        const hero = data.heroes.find((h: any) => h.id === id.toString());
        return hero || null;
      })
    );
  }
}
