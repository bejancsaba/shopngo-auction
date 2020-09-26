import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {AuthJwtServerProvider} from "../services/auth-jwt.service";

@Component({
  selector: 'app-root',
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class LandingComponent implements OnInit{
  userName = '';

  constructor(private authProvider: AuthJwtServerProvider) {
    this.userName = authProvider.getIdentity().sub;
  }

  ngOnInit(): void {
  }
}
