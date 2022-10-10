import { Component } from '@angular/core';
import { myBlog } from './myBlog';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-blog-post',
  templateUrl: './blog-post.component.html',
  styleUrls: ['./blog-post.component.css']
})

export class BlogPostComponent {

//  submitted =false;
  constructor(private http: HttpClient){}
  onClickSubmit(data: any){

    let headers = new HttpHeaders({
      'Access-Control-Allow-Origin': '*' });
  let options = { headers: headers };


    this.http.post('http://localhost:8080/api/posts', data, options)
    .subscribe((result)=>{
      console.warn("result",result)
    })

    // const model = new myBlog(data.id, data.content, data.description, data.title);

    // console.log(data);
    // console.log(model)
   }


  

}
