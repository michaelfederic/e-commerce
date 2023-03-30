import { Component } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-menu-navbar',
  templateUrl: './menu-navbar.component.html',
  styleUrls: ['./menu-navbar.component.css']
})
export class MenuNavbarComponent {
  constructor(private modalService: NgbModal){}

  openFullscreen(content: any){
    this.modalService.open(content, {fullscreen: true})
  }
}
