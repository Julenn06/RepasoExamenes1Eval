import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HouseRemoteService } from '../../house-remote-service';
import { House } from '../../house';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-house-detail',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './house-page.html',
  styleUrls: ['./house-page.css']
})
export class HouseDetailComponent implements OnInit {
  house!: House;
  houseId: string = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private houseService: HouseRemoteService
  ) {}

  ngOnInit() {
    this.houseId = this.route.snapshot.paramMap.get('id') || '';
    if (this.houseId) {
      this.loadHouse();
    }
  }

  loadHouse() {
    this.houseService.getHouseById(this.houseId).subscribe({
      next: (data) => {
        this.house = data;
      },
      error: (err) => {
        console.error('Error loading house:', err);
      }
    });
  }

  goBack() {
    this.router.navigate(['/home']);
  }

    updateHouse(house: House) {
    if (this.house) {
      // Validar que los datos obligatorios estén presentes
      if (!this.house.name || !this.house.propertyType || !this.house.location.city || !this.house.location.address) {
        alert('Por favor, completa todos los campos obligatorios');
        return;
      }

      // Validar precios si están marcados como disponibles
      if (this.house.isForSale && (!this.house.salePrice || this.house.salePrice <= 0)) {
        alert('El precio de venta debe ser mayor a 0');
        return;
      }

      if (this.house.isForRent && (!this.house.rentPrice || this.house.rentPrice <= 0)) {
        alert('El precio de alquiler debe ser mayor a 0');
        return;
      }

      // Actualizar la casa
      this.houseService.updateHouse(this.house).subscribe({
        next: (updatedHouse) => {
          console.log('Casa actualizada exitosamente:', updatedHouse);
          this.house = updatedHouse;
          alert('Casa actualizada exitosamente');
        },
        error: (error) => {
          console.error('Error al actualizar la casa:', error);
          alert('Error al actualizar la casa');
        }
      });
    }
  }
}