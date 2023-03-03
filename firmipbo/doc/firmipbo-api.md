# FIRMIPBO - api

## Generale

### BaseUrl {endpoint}
`http://localhost:8080/firmip/restfacade/be`

### Service
* GET /cmd/ping
* GET /cmd/version
* GET /cmd/build-info
* GET /comuni
* GET /tipi-documento
* GET /tipi-iniziativa


### BasicAuth {auth}
L'accesso è protetto.
?Shib-Iride-IdentitaDigitale=MRARSS70H17I138F/MARIO/ROSSI/SP//1//

## /cmd/ping:

Ping sul servizio (senza o con accesso al DB).

<http://localhost:10110/firmip/restfacade/be/ping?Shib-Iride-IdentitaDigitale=MRARSS70H17I138F/MARIO/ROSSI/SP//1//>

Request:

```
GET /cmd/ping HTTP/1.1
```

Response:

```
OK
```

<http://localhost:10110/firmip/restfacade/be/ping?db=true&Shib-Iride-IdentitaDigitale=MRARSS70H17I138F/MARIO/ROSSI/SP//1//>

Request:

```
GET /cmd/ping?db=true HTTP/1.1
```

Response:

```
Ping DB OK at 2022-04-14 19:24:11.987127+02
```

## /cmd/version:

Version.

<http://localhost:10110/firmip/restfacade/be/version?Shib-Iride-IdentitaDigitale=MRARSS70H17I138F/MARIO/ROSSI/SP//1//>

Request:

```
GET /cmd/version HTTP/1.1
```

Response:

```
1.0.0
```

## /cmd/build-info:

Recupero informazione del build.

<http://localhost:10110/firmip/restfacade/be/build-info?Shib-Iride-IdentitaDigitale=MRARSS70H17I138F/MARIO/ROSSI/SP//1//>

Request:

```
GET /cmd/build-info HTTP/1.1
```

Response:

```
{
    "version": "1.0.0",
    "buildTime": "2022-04-14 18:14:03:003 CEST",
    "target": "dev"
}
```

## /comuni:

* GET /comuni
* GET /comuni/123
* GET /comuni/by-codice-istat/001272

### Elenco degli comuni.

<http://localhost:10110/firmip/restfacade/be/comuni?Shib-Iride-IdentitaDigitale=MRARSS70H17I138F/MARIO/ROSSI/SP//1//>

Request:

```
GET /comuni HTTP/1.1
```

Response:

```
[
    {
        "codice": "001001",
        "nome": "Agliè"
    },
    {
        "codice": "001002",
        "nome": "Airasca"
    },
    ...
]
```

## /tipi-documento:

* GET /tipi-documento
* GET /tipi-documento/by-tipo/CI

### Elenco degli tipi di documento.

<http://localhost:10110/firmip/restfacade/be/tipi-documento?Shib-Iride-IdentitaDigitale=MRARSS70H17I138F/MARIO/ROSSI/SP//1//>

Request:

```
GET /tipi-documento HTTP/1.1
```

Response:

```
[
    {
        "tipo": "CI",
        "nome": "CARTA D'IDENTITA'"
    },
    {
        "tipo": "PA",
        "nome": "PATENTE DI GUIDA"
    },
    {
        "tipo": "CF",
        "nome": "CODICE FISCALE"
    }
]
```


## /tipi-iniziativa:

Elenco degli tipi di iniziativa.

<http://localhost:10110/firmip/restfacade/be/tipi-iniziativa?Shib-Iride-IdentitaDigitale=MRARSS70H17I138F/MARIO/ROSSI/SP//1//>

Request:

```
GET /tipi-iniziativa HTTP/1.1
```

Response:

```
[
    {
        "id": 1,
        "descrizione": "REFERENDUM"
    },
    {
        "id": 2,
        "descrizione": "PROPOSTA DI LEGGE"
    }
]
```