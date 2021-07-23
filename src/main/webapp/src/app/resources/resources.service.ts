import { Injectable } from '@angular/core';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Resource } from '../model/models';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({providedIn: 'root'}
	
)
export class ResourcesService {
	private baseUrl: string;
	
	constructor(private http: HttpClient){
		this.baseUrl = environment.apiUrl+'/resource/';
	}

	
	getResources(): Observable<Resource[]> {
		return this.http.get<Resource[]>(this.baseUrl);
			
	}
}
