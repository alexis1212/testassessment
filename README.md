# testassessment

This solution will run the api-playground application on localhost, run a selected set of tests on the application, and then close the application.

To run the full suite of tests, run the following script:

`./run.sh`

To run the full test suite for a certain endoint, execute:

`./run.sh {ENDPOINT}`

To run a specific set of tests, run the command in the following pattern:

`./run.sh {ENDPOINT} {TAG}`

where:
- endpoint matches the certain endpoint in API (in this case products)
- tag matches the appropriate tag found in the tests


EXAMPLE:

To run all product related scenario tests, run the following command:

`./run.sh products @products`

To run smoke tests related to product endpoint, execute:

`./run.sh products @smoke`

# DOCKER

To run the application using Docker, and remove docker container after the tests are executed, in any of the above mentioned commands, replace `./run.sh` with `./docker-run.sh`.

EXAMPLE:

`./docker-run.sh products @products`

# REPORTS:

After the tests were executed, html test results are stored in a report located in `target/reports/test-report/index.html`.
