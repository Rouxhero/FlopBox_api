<p align="center">
<img src="https://see.fontimg.com/api/renderfont4/1GVgg/eyJyIjoiZnMiLCJoIjoxMTYsInciOjEwMDAsImZzIjoxMTYsImZnYyI6IiM5ODc0MDgiLCJiZ2MiOiIjRkZGRkZGIiwidCI6MX0/RmxvcEJveA/kingsman-demo.png">
</p>

![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/Rouxhero/FlopBox_api?style=plastic)
![GitHub release (latest by date)](https://img.shields.io/github/v/release/Rouxhero/FlopBox_api?display_name=tag&style=plastic)
![PingPong status](https://img.shields.io/pingpong/status/sp_68ccf5a6b18641939e8632283f1be487?label=public%20api%20)
# Sommaire
- [Sommaire](#sommaire)
- [Liste des paths](#liste-des-paths)

# Liste des paths

| Path                     | Access | Header                                                   | Query | path         | Body Form                           | Response  Type                                                                     | Response Body   |
|--------------------------|--------|----------------------------------------------------------|-------|--------------|-------------------------------------|------------------------------------------------------------------------------------|-----------------|
| /auth/register           | POST   | None                                                     | None  | None         | *username*<br>*password*<br>*email* | text/plain                                                                         | **API Key**     |
| /auth/login              | POST   | None                                                     | None  | None         | *username*<br>*password*            | text/plain                                                                         | **API Key**     |
| /server                  | GET    | API Key                                                  | None  | None         | None                                | application/json                                                                   | **Server List** |
| /server                  | POST   | API Key                                                  | None  | None         | *host*<br>*port*                    | text/plain                                                                         | **Message**     |
| /server/:sid             | GET    | API Key <br> user <br> pass <br> Accept                  | None  | sid          | None                                | application/json<br>application/xml                                                | **Folder List** |
| /server/:sid             | DELETE | API Key                                                  | None  | sid          | None                                | text/plain                                                                         | **Message**     |
 | /server/:sid/folder/:fid | GET    | API Key <br> user <br> pass <br> Accept                  | None  | sid <br> fid | None                                | application/json<br>application/xml                                                | **Folder List** |
| /server/:sid/folder/:fid | DELETE | API Key <br> user <br> pass <br> Accept                  | None  | sid <br> fid | None                                | text/plain                                                                         | **Message**     |
| /server/:sid/folder/:fid | PUT    | API Key <br> user <br> pass <br> Accept <br>Content-Type | None  | sid <br> fid | *name*                              | text/plain                                                                         | **Message**     |
 | /server/:sid/file/:fid   | GET    | API Key <br> user <br> pass <br> Accept                  | None  | sid <br> fid | None                                | application/octet-stream <br> text/plain <br>application/json <br> application/xml | **File**        |
 | /server/:sid/file/:fid   | POST   | API Key <br> user <br> pass                              | None  | sid <br> fid | *file*                              | text/plain                                                                         | **Message**     |
 | /server/:sid/file/:fid   | DELETE | API Key <br> user <br> pass <br> Accept                  | None  | sid <br> fid | None                                | text/plain                                                                         | **Message**     |
 | /server/:sid/file/:fid   | PUT    | API Key <br> user <br> pass <br> Accept <br>Content-Type | None  | sid <br> fid | *name*                              | text/plain                                                                         | **Message**     |
 | /server/:sid/upload/:fid | POST   | API Key <br> user <br> pass                              | None  | sid <br> fid | *file*<br>*name*                    | text/plain                                                                         | **Message**     |
 | /server/:sid/upload      | POST   | API Key <br> user <br> pass                              | None  | sid          | *file*<br>*name*                    | text/plain                                                                         | **Message**     |
 | /server/search           | GET    | API Key <br> user <br> pass <br> Accept                  | s     | None         | None                                | application/json<br>application/xml                                                | **File List**   |

