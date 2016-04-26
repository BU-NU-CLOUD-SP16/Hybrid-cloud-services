# Authors: Surekha Jadhwani
library(ggplot2)
data <- read.csv("results.csv", header = FALSE, sep = ",")
data
names(data) <- c("Task", "TimeInMinutes")
ggplot(data, aes(x = Task, y = TimeInMinutes)) + geom_bar(stat = "identity", fill = "blue")  
