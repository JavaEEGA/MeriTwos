
# MeriTwos

This is a platform to connect students and companies looking for lia-collaborations

## API References

#### User requests

```http
  GET /api/users
  GET /api/users/{id}
  POST /api/users
  PUT /api/users/{id}
  DELETE /api/users/{id}

```
#### Student requests

```http
  GET /api/students
  GET /api/students/{id}
  POST /api/students
  POST /api/students/{studentid}/{adid}
  POST /api/students/newStudent
  DELETE /api/students/{id}

```
#### Company requests
```http
  GET /api/companies
  GET /api/companies/{id}
  POST /api/companies
  POST /api/companies/{companyId}/newAd
  POST /api/companies/newCompany
  POST /api/companies/newAd
  PUT /api/companies/{id}
  DELETE /api/companies/{id}

```
#### Ad requests
```http
  GET /api/ads
  GET /api/ad/{id}
  POST /api/ads
  DELETE /api/ads/{id}

```

## Site paths

#### Templates
```http
/ads
/newstudent
/newad
/newcompany
```
