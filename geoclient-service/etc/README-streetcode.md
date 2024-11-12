# Street Code Endpoints

## Why are there two endpoints?

TBD

Both endpoints use different Geosupport functions based on input:

| Input   | Geosupport Function |
| --------| ------------------- |
| `B5SC`  | `D`                 |
| `B7SC`  | `DG`                |
| `B10SC` | `DN`                |

### `/streetcode`

Implemented by `gov.nyc.doitt.gis.geoclient.service.web.RestController`.

Top-level property: `streetcode`

```json
{
    "streetcode": {
        "boroughCode1In": "1",
        "firstStreetCode": "12969001010",
        "firstStreetNameNormalized": "RIVERSIDE DRIVE",
        "geosupportFunctionCode": "D",
        "geosupportReturnCode": "00",
        "streetCode1In": "29690",
        "streetNameNormalizationFormatFlagIn": "S",
        "streetNameNormalizationLengthLimitIn": "32",
        "workAreaFormatIndicatorIn": "C"
    }
}
```

### `/streetcode/b5sc`

Implemented by `gov.nyc.doitt.gis.geoclient.service.street.web.StreetCodeController`.

Top-level property: `response`

```json
{
    "response": {
        "boroughCode1In": "1",
        "firstStreetCode": "12969001010",
        "firstStreetNameNormalized": "RIVERSIDE DRIVE",
        "geosupportFunctionCode": "D",
        "geosupportReturnCode": "00",
        "streetCode1In": "29690",
        "streetNameNormalizationFormatFlagIn": "S",
        "streetNameNormalizationLengthLimitIn": "32",
        "workAreaFormatIndicatorIn": "C"
    }
}
```
