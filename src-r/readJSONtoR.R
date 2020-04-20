### Reading OSM data saved as json to R

# load necessary packages
require(RJSONIO)
require(tidyverse)

#set working directory to directory with json file
setwd("directory/of/json/data")

OSMjsonToDataframe <- function(file = file) {
  # read json to tibble in R
  data <-
    RJSONIO::fromJSON(file, nullValue = NA)$data %>% # read json to R as list
    transpose() %>% # transpose list to shape of data frame / tibble
    as_tibble() %>% # convert into tibble
    mutate_at(.vars = vars(-starts_with("Tags")),# encode lists as vectors
              .funs = function(x)unlist(x))
  
  # sort variables into a meaningful order
  sorting <- c(
    sort(str_subset(colnames(data), "ID", negate = FALSE), decreasing = TRUE),
    sort(str_subset(colnames(data), "Timestamp", negate = FALSE)),
    sort(str_subset(colnames(data), "Changes", negate = FALSE)),
    str_subset(colnames(data), "Contribution", negate = FALSE),
    sort(str_subset(colnames(data), "Before", negate = FALSE)),
    sort(str_subset(colnames(data), "After", negate = FALSE)))
  data <- data %>% 
    select(all_of(sorting))
  
  #convert timestamp from character to integer time format
  if("Timestamp" %in% colnames(data)){
    data$Timestamp <-
      parse_datetime(as.character(data$Timestamp))
  }

  #convert all columns to according format
  data <- data %>%
    mutate_at(.vars = vars(contains("Centroid")), #CentroidLatBefore,CentroidLatAfter,CentroidLonBefore,CentroidLonAfter
              .funs = function(x)as.numeric(x)) %>%
    mutate_at(.vars = vars(contains("GeometryType")), #GeometryTypeBefore, GeometryTypeAfter
              .funs = function(x)as.factor(x)) %>%
    mutate_at(.vars = vars(contains("NumberOfTags")), #NumberOfTagsBefore, NumberOfTagsAfter
              .funs = function(x)(x/2))
  
  
  #compute tags with empty list for no entry
  if("TagsBefore" %in% colnames(data)){
    TagsBeforeR <- list()
    for(i in 1: length(data$OsmID)){
      if(is.na(data$TagsBefore[[i]][1])){ #detect NAs
        TagsBeforeR[[i]] <- vector("character", 0L) #change NA to empty character vector
        }else{
          TagsBeforeR[[i]] <- data$TagsBefore[[i]]
        }
      }
    data$TagsBefore <- TagsBeforeR
  }
  
  if("TagsAfter" %in% colnames(data)){
    TagsAfterR <- list()
    for(i in 1: length(data$OsmID)){
      if(is.na(data$TagsAfter[[i]][1])){ #detect NAs
        TagsAfterR[[i]] <- vector("character", 0L) #change NA to empty character vector
      }else{
        TagsAfterR[[i]] <- data$TagsAfter[[i]]
      }
    }
    data$TagsAfter <-  TagsAfterR
  }
  
  if("Tags" %in% colnames(data)){
    TagsR <- list()
    for(i in 1: length(data$OsmID)){
      if(is.na(data$Tags[[i]][1])){ #detect NAs
        TagsR[[i]] <- vector("character", 0L) #change NA to empty character vector
      }else{
        TagsR[[i]] <- data$Tags[[i]]
      }
    }
    data$Tags <-  TagsR
  }
  
  return(data)
}

data <-
  OSMjsonToDataframe("data-file.json")
