import {
  Component
} from '@angular/core';
import {
  Router
} from '@angular/router';
import {
  FormsModule
} from '@angular/forms';
import {
  CommonModule
} from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.html',
  styleUrls: ['./login.css']
})
export class Login {
  user: string = '';
  password: string = '';

  constructor(private router: Router) {}

  login() {

    if (this.user != this.password || this.user == '' || this.password == '') {
      alert("maaaaal");
    } else if (this.user == "register" && this.password == "register") {
      this.router.navigate(['/register']);

    } else {
      this.router.navigate(['/prueba']);
    }
  }
}
