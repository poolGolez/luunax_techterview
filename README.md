### Questions
1. What purpose does it serve having the `Vehicle` interface? As far as the current domain, each subclass doesn't really
have polymorphic behaviour; only different data. A simple class `Vehicle` containing a String property type would suffice 
the use case.

### Summary
* Exposed the calculation as an HTTP endpoint:
  * Method: `POST /tax`
  * Body:
  ```json
  {
    "vehicleType": "Motorbikex",
    "dates": [
        "2013-01-14 21:00:00",
        "2013-01-15 21:00:00",
        "2013-02-07 06:23:27",
        "2013-02-07 15:27:00",
        "2013-02-08 06:27:00",
        "2013-02-08 06:20:27",
        "2013-02-08 14:35:00",
        "2013-02-08 15:29:00",
        "2013-02-08 15:47:00",
        "2013-02-08 16:01:00",
        "2013-02-08 16:48:00",
        "2013-02-08 17:49:00",
        "2013-02-08 18:29:00",
        "2013-02-08 18:35:00",
        "2013-03-26 14:25:00",
        "2013-03-28 14:07:27"
    ]
  }
  ```

* Applied various bug fixes on `CongestionTaxCalculator`:
  * Reset `intervalStart` to ensure that it is reset after 1 hour interval
  * Fix fee rate for 8:30 - 14:59 schedule
* Extract actual computation of toll fee for a vehicle in the `InMemoryTollFeeProvider` with the `TollFeeProvider` as interface
* Added unit tests for `CongestionTaxCalculator` and `InMemory


### Improvements
1. Tax should have been `BigDecimal` instead of `int` to more accurately reflect it's properties.
2. The `60` per day tax cap still persists. I ran out of time to fix it. But given the resources, I would have changed the implemenation of CongestionTaxCalculator to first group the dates by day before processing the applicable toll fee for that day. It would be easier to maintain the code.
3. I would have added integration tests per API covering happy paths. Integration tests are more resilient to refactors compared to unit tests.
4. I would have made another file based `TollFeeProvider`.
5. I would have used 3rd party libraries for Date and time manipulation rather than having the manual logic. This reduces bugs and allows us to focus more on business logic.