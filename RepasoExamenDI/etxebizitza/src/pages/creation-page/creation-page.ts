import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { HouseRemoteService } from '../../house-remote-service';
import { House, HouseTypes } from '../../house';

@Component({
  selector: 'app-creation-page',
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './creation-page.html',
  styleUrl: './creation-page.css',
})
export class CreationPage {
  cont: number = 16;
  private fb = inject(FormBuilder);
  private router = inject(Router);
  private houseService = inject(HouseRemoteService);

  houseForm: FormGroup = this.fb.group({
    name: ['', [Validators.required, Validators.minLength(3)]],
    propertyType: ['', Validators.required],
    location: this.fb.group({
      city: ['', Validators.required],
      address: ['', Validators.required],
      lat: [0],
      long: [0]
    }),
    isForSale: [false],
    salePrice: [0],
    isForRent: [false],
    rentPrice: [0],
    picture: ['']
  });

  onSubmit() {
    if (this.houseForm.valid) {
      const formValue = this.houseForm.value;
      
      const newHouse: House = {
        id: this.cont.toString(), // Convertir a string y usar ':'
        name: formValue.name,
        propertyType: formValue.propertyType as HouseTypes,
        location: formValue.location,
        isForSale: formValue.isForSale,
        salePrice: formValue.isForSale ? formValue.salePrice : 0,
        isForRent: formValue.isForRent,
        rentPrice: formValue.isForRent ? formValue.rentPrice : 0,
        picture: formValue.picture || ''
      };

      console.log('Nueva casa:', newHouse);
      
      this.houseService.createHouse(newHouse).subscribe({
        next: (createdHouse) => {
          console.log('Casa creada exitosamente:', createdHouse);
          this.cont++; // Incrementar contador para siguiente casa
          this.router.navigate(['/home']);
          this.cont+1;
        },
        error: (error) => {
          console.error('Error al crear la casa:', error);
        }
      });

    } else {
      console.log('Formulario inválido');
      this.markFormGroupTouched();
    }
  }

  onCancel() {
    this.router.navigate(['/home']);
  }

  private markFormGroupTouched() {
    Object.keys(this.houseForm.controls).forEach(key => {
      const control = this.houseForm.get(key);
      control?.markAsTouched();
      
      if (control instanceof FormGroup) {
        Object.keys(control.controls).forEach(nestedKey => {
          control.get(nestedKey)?.markAsTouched();
        });
      }
    });
  }
}