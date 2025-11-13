import { Component, OnInit, inject } from '@angular/core';
import { House, HouseTypes } from '../../house';
import { HouseRemoteService } from '../../house-remote-service';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms'; // Agregar para ngModel

@Component({
  selector: 'app-home-page',
  imports: [CommonModule, RouterModule, FormsModule], // Agregar FormsModule
  templateUrl: './home-page.html',
  styleUrl: './home-page.css',
})
export class HomePage implements OnInit {

  houses: House[] = [];
  selling: House[] = [];
  selectedHouseType: HouseTypes | 'ALL' = 'ALL'; // Corregir nombre de variable

  // Hacer el enum disponible en el template
  houseTypes = Object.values(HouseTypes);

  constructor(
    private houseRemoteService: HouseRemoteService,
    private router: Router
  ) {}

  ngOnInit() {
    this.loadAllHouses();
    this.houseRemoteService.getSellingHouses().subscribe({
      next: (sellingData) => {
        this.selling = sellingData;
        console.log("Houses selling received:", sellingData);
      }, 
      error: (error) => {
        console.error('Error fetching selling houses', error);
      }
    }); // Cambiar : por ;
  }

  loadAllHouses() {
    this.houseRemoteService.getHouses().subscribe({
      next: (data) => {
        this.houses = data;
        console.log('Houses received:', data);
      },
      error: (error) => {
        console.error('Error fetching houses:', error);
      }
    });
  }

  onHouseTypeChange() {
    if (this.selectedHouseType === 'ALL') {
      this.loadAllHouses();
    } else {
      this.houseRemoteService.getHousesByType(this.selectedHouseType as HouseTypes).subscribe({
        next: (data) => {
          this.houses = data;
        },
        error: (error) => {
          console.error('Error loading houses by type:', error);
        }
      });
    }
  }

  deleteHouse(id: string) {
    if (confirm('¿Estás seguro de que quieres eliminar esta casa?')) {
      this.houseRemoteService.deleteHouse(id).subscribe({
        next: () => {
          console.log('Casa eliminada exitosamente');
          // Recargar las listas después de eliminar
          this.loadHouses();
        },
        error: (error) => {
          console.error('Error al eliminar la casa:', error);
        }
      });
    }
  }

  updateHouse(house: House) {
    this.houseRemoteService.updateHouse(house).subscribe({
      next: (updatedHouse) => {
        console.log('Casa actualizada exitosamente:', updatedHouse);
        this.loadHouses();
      },
      error: (error) => {
        console.error('Error al actualizar la casa:', error);
      }
    });
  }
  
  private loadHouses() {
    // Recargar todas las casas
    this.houseRemoteService.getHouses().subscribe({
      next: (data) => {
        this.houses = data;
      }
    });

    // Recargar casas en venta
    this.houseRemoteService.getSellingHouses().subscribe({
      next: (sellingData) => {
        this.selling = sellingData;
      }
    });
  }

  goToHouseDetails(id: string) {
    this.router.navigate(['/house', id]);
  }

} 