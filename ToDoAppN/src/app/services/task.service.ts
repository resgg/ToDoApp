import { HttpClient} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  private url :string =  "http://localhost:8080/task"
  constructor(private http: HttpClient) { }
  
  public getTaskByUserId(userId: any): Observable<any> {
    console.log(userId);
    return this.http.get(this.url + "/getTasks", { params: { userId: userId } });
}
  
  public getString():Observable<any>{
    return this.http.get(this.url + "/isitworking",{ responseType: 'text' })
  }

  public createTask(task:any):Observable<any>{
    return this.http.post(this.url + "/create",task)
  }
  
  public changeStatus(taskId: number, statusUpdate: { status: string }):Observable<any>{
    return this.http.post(this.url+"/changeStatus/"+ taskId, statusUpdate);
  }

}
