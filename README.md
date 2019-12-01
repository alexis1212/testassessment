# testassessment

To run the full suite of tests, run the following script:

`./run.sh`

To run a specific set of tests, run the command in the following pattern:

`./run.sh {ENDPOINT} {TARGET}`

where:
- endpoint matches the certain endpoint in API (in this case products)
- target matches the appropriate tag found in the tests

To run all product related scenario tests, run the following command:

`./run.sh products @products`
