import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-first-view',
  imports: [],
  templateUrl: './first-view.html',
  styleUrl: './first-view.css',
})
export class FirstView {

    constructor(private router: Router) {}

  goToLogin() {
    this.router.navigate(['/login']);
  }
}
