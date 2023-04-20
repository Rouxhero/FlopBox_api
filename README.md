<p align="center">
<img src="https://see.fontimg.com/api/renderfont4/1GVgg/eyJyIjoiZnMiLCJoIjoxMTYsInciOjEwMDAsImZzIjoxMTYsImZnYyI6IiM5ODc0MDgiLCJiZ2MiOiIjRkZGRkZGIiwidCI6MX0/RmxvcEJveA/kingsman-demo.png">
</p>
<p align="center">
<img src="https://img.shields.io/github/languages/code-size/Rouxhero/FlopBox_api?style=plastic">
<img src="https://img.shields.io/github/v/release/Rouxhero/FlopBox_api?display_name=tag&style=plastic">
<img src="https://img.shields.io/website?down_color=red&down_message=error&label=Public%20api%20state%20&up_message=online&url=http%3A%2F%2Fapi.givemecoffee.ninja%3A8080%2Fstatus">
</p>

# Sommaire
- [Sommaire](#sommaire)
- [Liste des paths](#liste-des-paths)

# Liste des paths


| Path                            | Access | Header                                  | Query | path         | Body Form                           | Response  Type                                                                     | Response Body   |
|---------------------------------|--------|-----------------------------------------|-------|--------------|-------------------------------------|------------------------------------------------------------------------------------|-----------------|
| [/status]()                     | GET    | None                                    | None  | None         |                                     | text/HTML                                                                          | **Page**        |
| [/auth/register]()              | POST   | None                                    | None  | None         | *username*<br>*password*<br>*email* | text/plain                                                                         | **API Key**     |
| [/auth/login]()                 | POST   | None                                    | None  | None         | *username*<br>*password*            | text/plain                                                                         | **API Key**     |
| [/server]()                     | GET    | API Key                                 | None  | None         | None                                | application/json                                                                   | **Server List** |
| [/server]()                     | POST   | API Key                                 | None  | None         | *alias*<br>*host*<br>*port*         | text/plain                                                                         | **Message**     |
| [/server/:sid]()                | GET    | API Key <br> user <br> pass <br> Accept | None  | sid          | None                                | application/json<br>application/xml                                                | **Folder List** |
| [/server/:sid]()                | DELETE | API Key                                 | None  | sid          | None                                | text/plain                                                                         | **Message**     |
 | [ /server/:sid/folder/:fid]()   | GET    | API Key <br> user <br> pass <br> Accept | None  | sid <br> fid | None                                | application/json<br>application/xml                                                | **Folder List** |
| [/server/:sid/folder/:fid]()    | DELETE | API Key <br> user <br> pass             | None  | sid <br> fid | None                                | text/plain                                                                         | **Message**     |
| [/server/:sid/folder/:fid]()    | PUT    | API Key <br> user <br> pass             | None  | sid <br> fid | *name*                              | text/plain                                                                         | **Message**     |
| [/server/:sid/folder/:fid]()    | POST   | API Key <br> user <br> pass             | None  | sid <br> fid | *name*                              | text/plain                                                                         | **Message**     |
 | [ /server/:sid/file/:fid]()     | GET    | API Key <br> user <br> pass <br> Accept | None  | sid <br> fid | None                                | application/octet-stream <br> text/plain <br>application/json <br> application/xml | **File**        |
 | [ /server/:sid/file/:fid]()     | POST   | API Key <br> user <br> pass             | None  | sid <br> fid | *file*                              | text/plain                                                                         | **Message**     |
 | [ /server/:sid/file/:fid]()     | DELETE | API Key <br> user <br> pass             | None  | sid <br> fid | None                                | text/plain                                                                         | **Message**     |
 | [ /server/:sid/file/:fid]()     | PUT    | API Key <br> user <br> pass             | None  | sid <br> fid | *name*                              | text/plain                                                                         | **Message**     |
 | [ /server/:sid/upload/:fid]()   | POST   | API Key <br> user <br> pass             | None  | sid <br> fid | *file*                              | text/plain                                                                         | **Message**     |
 | [ /server/:sid/upload/f/:fid]() | POST   | API Key <br> user <br> pass             | None  | sid <br> fid | *name*                              | text/plain                                                                         | **Message**     |
 | [ /server/:sid/upload]()        | POST   | API Key <br> user <br> pass             | None  | sid          | *file*                              | text/plain                                                                         | **Message**     |
 | [ /server/:sid/upload/f/]()     | POST   | API Key <br> user <br> pass             | None  | sid          | *name*                              | text/plain                                                                         | **Message**     |
 | [ /server/search]()             | GET    | API Key <br> user <br> pass <br> Accept | s     | None         | None                                | application/json<br>application/xml                                                | **File List**   |

