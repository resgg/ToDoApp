import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private url :string =  "http://localhost:8080/api"
  constructor(private http: HttpClient) { }

  public getString():Observable<any>{
     return this.http.get(this.url + "/get",{ responseType: 'text' })
  }
  
  public login(form:any):Observable<any>{
    return this.http.post(this.url+"/authenticate",form)
  }
  public signup(form:any):Observable<any>{
    return this.http.post(this.url + "/createUser",form)
  }
}
