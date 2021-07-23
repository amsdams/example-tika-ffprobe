import { Component, OnInit } from '@angular/core';
import { Resource } from '../model/models';
import { ResourcesService } from   './resources.service'

@Component({
  selector: 'app-resources',
  templateUrl: './resources.component.html',
  styleUrls: ['./resources.component.css']
})
export class ResourcesComponent implements OnInit {

  constructor(private resourcesService:ResourcesService ) { }
   resources!: Resource[];

  ngOnInit(): void {
    this.getResources();
  }

  getResources(): void {
    this.resourcesService.getResources().subscribe(data=>{
      this.resources = data
    }
    );

  }
}
