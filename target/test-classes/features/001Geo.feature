Feature: OpenWeather Geocoding API Utility

#1
  Scenario Outline: Successfully retrieve geocoding information using a city name
    Given I specify the city name as "<city>"
    When I request geocoding details from the API
    Then the response should contain valid latitude and longitude for "<city>"

    Examples:
      | city          |
      | New York       |
      | Los Angeles    |
      | San Francisco  |
      
#2
  Scenario Outline: Successfully retrieve geocoding information using a postal code
    Given I specify the postal code as "<postalCode>"
    When I request geocoding details from the API "<postalCode>"
    Then the response should contain valid latitude and longitude for postal code "<postalCode>"

    Examples:
      | postalCode |
      | 10001      |
      | 90001      |
      | 94105      |

#3
  Scenario Outline: Handle invalid city name gracefully
    Given I specify an invalid city name as "<invalidCity>"
    When I request geocoding details from the API "<invalidCity>"
    Then the response should indicate that no location was found for "<invalidCity>"

    Examples:
      | invalidCity |
      | Atlantis    |
      | Neverland   |
      | Narnia      |

#4
  Scenario Outline: Handle invalid postal code gracefully
    Given I specify an invalid postal code as "<invalidPostalCode>"
    When I request geocoding details from the API
    Then the response should indicate that no location was found for postal code "<invalidPostalCode>"

    Examples:
      | invalidPostalCode |
      | 999999             |
      | 000000             |
      | 888888             |

#5
  Scenario Outline: Retrieve geocoding information for a city with special characters
    Given I specify the city name as "<cityWithSpecialChars>"
    When I request geocoding details from the API
    Then the response should contain valid latitude and longitude for "<cityWithSpecialChars>"

    Examples:
      | cityWithSpecialChars |
      | San José             |
      | São Paulo            |
      | Montréal              |

#6
  Scenario Outline: Retrieve geocoding information for a city with multiple matches
    Given I specify the city name as "<cityWithMultipleMatches>"
    When I request geocoding details from the API
    Then the response should return the geocoding details for the first found "<cityWithMultipleMatches>" 

    Examples:
      | cityWithMultipleMatches |
      | Springfield              |
      | Greenville                |
      | Jackson                   |


 