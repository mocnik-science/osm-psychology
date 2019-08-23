### Reading OSM data saved as json to R

# load necessary packages
require(RJSONIO)
require(tidyverse)

#set working directory to directory with json file
setwd("directory/of/json/data")

OSMjsonToDataframe <- function(file = file) {
  # read json to tibble in R
  data <-
    RJSONIO::fromJSON(
      file, nullValue = NA)$data %>% # read json to R as list
    transpose() %>% # transpose list to shape of data frame / tibble
    as_tibble() %>% # convert into tibble
    mutate_at(.vars = vars(-TagsAfter,-TagsBefore),# encode lists as vectors
              .funs = function(x)unlist(x))
  
  # sort variables into a meaningful order
  sorting <- c(
    sort(str_subset(colnames(data), "ID", negate = FALSE), decreasing = TRUE),
    sort(str_subset(colnames(data), "Timestamp", negate = FALSE)),
    str_subset(colnames(data), "Contribution", negate = FALSE),
    sort(str_subset(colnames(data), "Before", negate = FALSE)),
    sort(str_subset(colnames(data), "After", negate = FALSE)))
  data <- data %>% 
    select(sorting)
  
  #convert timestamp from character to integer time format
  data$Timestamp <-
  as.POSIXct(
    gsub("T", " ", as.character(data$Timestamp)), 
    format = "%Y-%m-%d %H:%M:%S")
  
  return(data)
}

data <-
  OSMjsonToDataframe("data-file.json")