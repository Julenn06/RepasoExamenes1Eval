import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule, Router } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './register.html',
  styleUrls: ['./register.css'],
})
export class Register {
  name: string = '';
  email: string = '';
  password: string = '';

  constructor(private router: Router) {}

  register() {
    if (!this.name || !this.email || !this.password) {
      alert('Por favor rellena todos los campos');
      return;
    }

    // Implementación mínima: muestra datos y redirige al login
    alert(`Cuenta creada para ${this.name} (${this.email})`);
    this.router.navigate(['/login']);
  }

}
