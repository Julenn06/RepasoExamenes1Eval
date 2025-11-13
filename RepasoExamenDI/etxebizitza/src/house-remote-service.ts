import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { House, HouseTypes } from './house';

@Injectable({
  providedIn: 'root',
})
export class HouseRemoteService {
  
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:3000/property';

  getHouses(): Observable<House[]> {
    return this.http.get<House[]>(this.apiUrl)
  }

  getSellingHouses(): Observable<House[]> {
    return this.http.get<House[]>(this.apiUrl).pipe(
      map(houses => houses.filter(house => house.isForSale))
    );
  }

  getRentingHouses(): Observable<House[]> {
    return this.http.get<House[]>(this.apiUrl).pipe(
      map(houses => houses.filter(house => house.isForRent))
    );
  }

  createHouse(house: House): Observable<House>{
    const data = {id: `${house.id}`, name: house.name, location: house.location, propertyType: house.propertyType, isForSale: house.isForSale, salePrice: house.salePrice || "0", isForRent: house.isForRent, rentPrice: house.rentPrice || "0", picture: house.picture}
    return this.http.post<House>(this.apiUrl, data)
  }

  deleteHouse(id: string): Observable<any> {
      return this.http.delete(`${this.apiUrl}/${id}`);
  }

  updateHouse(house: House): Observable<House> {
    const data = {id: `${house.id}`, name: house.name, location: house.location, propertyType: house.propertyType, isForSale: house.isForSale, salePrice: house.salePrice || "0", isForRent: house.isForRent, rentPrice: house.rentPrice || "0", picture: house.picture}
    return this.http.put<House>(`${this.apiUrl}/${house.id}`, data);
  }

  getHouseById(id: string): Observable<House> {
    console.log('Navigating to house with id:', id);
    return this.http.get<House>(`${this.apiUrl}/${id}`);
  }

  getHousesByType(type: HouseTypes): Observable<House[]> {
    return this.http.get<House[]>(this.apiUrl).pipe(
      map(houses => houses.filter(house => house.propertyType === type))
    );
  }

  getHousesPriceOver100k(): Observable<House[]> {
    return this.http.get<House[]>(this.apiUrl).pipe(
      map(houses => houses.filter(houses => houses.salePrice > 100000 ))
    );
  }

  getNextId(): Observable<number> {
    return this.http.get<House[]>(this.apiUrl).pipe(
      map(houses => {
        const ids = houses.map(house => parseInt(house.id, 10));
        return Math.max(...ids) + 1;
      })
    );
  }

  //EN CASO DE QUE QUERAMOS CONTROLAR SI EL ID EXISTE
  /*createHouse(house: House): Observable<House> {
  // Primero verificar si el ID ya existe
  return this.checkIdExists(house.id).pipe(
    switchMap(exists => {
      if (exists) {
        // Si existe, generar un nuevo ID
        return this.getNextId().pipe(
          switchMap(newId => {
            const updatedHouse = { ...house, id: newId };
            return this.createHouseWithData(updatedHouse);
          })
        );
      } else {
        // Si no existe, crear con el ID actual
        return this.createHouseWithData(house);
      }
    })
  );
}

private checkIdExists(id: string): Observable<boolean> {
  return this.http.get<House>(`${this.apiUrl}/${id}`).pipe(
    map(() => true), // Si no da error, el ID existe
    catchError(() => of(false)) // Si da error 404, el ID no existe
  );
}

private createHouseWithData(house: House): Observable<House> {
  const data = {
    id: house.id, 
    name: house.name, 
    location: house.location, 
    propertyType: house.propertyType, 
    isForSale: house.isForSale, 
    salePrice: house.isForSale ? house.salePrice : null,
    isForRent: house.isForRent, 
    rentPrice: house.isForRent ? house.rentPrice : null,
    picture: house.picture
  };
  return this.http.post<House>(this.apiUrl, data);
}*/

}
