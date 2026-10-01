# Street Code Endpoints

## Why are there two endpoints?

* Endpoint `/streetcode/b5sc` returns results in a top-level **`response`** property/element.
* Endpoint `/streetcode` returns results in a top-level **`streetcode`** property/element.
* Both endpoints accept up to three `B5SC`, `B7SC`, and `B10SC` arguments.
* If two or more street code arguments are given, they must be of the same type. Otherwise the returned values may reflect only one arbitrarily chosen type.

Both endpoints use different Geosupport functions based on input. For example, the address "2019 7th Avenue, Manhattan" is north of Central Park. In this location, a more "appropriate" name for 7th Avenue is (as reflected by street signs) "Adam Clayton Powell JR Boulevard" although both are considered valid.

| Input   | Geosupport Function | Name Returned         | Example Code | Example Name                     |
| --------| ------------------- | --------------------- | ------------ | -------------------------------- |
| `B5SC`  | `D`                 | Primary street name   | 110610       | 7 AVENUE                         |
| `B7SC`  | `DG`                | Principle street name | 11061001     | ADAM CLAYTON POWELL JR BOULEVARD |
| `B10SC` | `DN`                | Principle street name | 11061001100  | ADAM CLAYTON POWELL JR BOULEVARD |

**NOTE:** Some DCP Geosupport functions return a "BOE preferred street name" which is the name for the street that the Board of Elections uses for the address of voting sites. This is different than the "primary" and "principle" street names.

### `/streetcode`

Top-level property: `streetcode`

**B5SC Example:**

Input B5SC: `110610`

```HTTP
https://api.nyc.gov/geoclient/v2/streetcode?streetCode=110610&borough=1&key=S3CRET
```

```json
{
    "streetcode": {
        "boroughCode1In": "1",
        "firstStreetCode": "11061004010",
        "firstStreetNameNormalized": "7 AVENUE",
        "geosupportFunctionCode": "D",
        "geosupportReturnCode": "00",
        "streetCode1In": "10610",
        "streetNameNormalizationFormatFlagIn": "S",
        "streetNameNormalizationLengthLimitIn": "32",
        "workAreaFormatIndicatorIn": "C"
    }
}
```

**B7SC Example:**

Input B7SC: `11061001`

```HTTP
https://api.nyc.gov/geoclient/v2/streetcode?streetCode=11061001&borough=1&key=S3CRET
```

```json
{
    "streetcode": {
        "boroughCode1In": "1",
        "firstStreetCode": "11061001100",
        "firstStreetNameNormalized": "ADAM CLAYTON POWELL JR BOULEVARD",
        "geosupportFunctionCode": "DG",
        "geosupportReturnCode": "00",
        "streetCode1In": "1061001",
        "streetNameNormalizationFormatFlagIn": "S",
        "streetNameNormalizationLengthLimitIn": "32",
        "workAreaFormatIndicatorIn": "C"
    }
}
```

### `/streetcode/b5sc`

Top-level property: `response`

Input B5SC: `110610`

```HTTP
https://api.nyc.gov/geoclient/v2/streetcode/b5sc?streetCode=110610&borough=1&key=S3CRET
```

```json
{
    "response": {
        "boroughCode1In": "1",
        "firstStreetCode": "11061004010",
        "firstStreetNameNormalized": "7 AVENUE",
        "geosupportFunctionCode": "D",
        "geosupportReturnCode": "00",
        "streetCode1In": "10610",
        "streetNameNormalizationFormatFlagIn": "S",
        "streetNameNormalizationLengthLimitIn": "32",
        "workAreaFormatIndicatorIn": "C"
    }
}
```
