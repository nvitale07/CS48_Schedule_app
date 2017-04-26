Read Me file last updated 4/25/17 by Nick Vitale

Contributors: 
Nick Vitale (Anthropology-Dance completed as of 4/25/17)


- - - - - - - - - - - - - - - - - - - - - - - - - - - -
HOW TO READ A CLASS RATINGS FILE

Each line of a class ratings file has three pieces of data separated by commas.

The Course ID, The Instructor, and Instructor’s Rating
- - - - - - - - - - - - - - - - - - - - - - - - - - - -
*The Course ID*
is the exact course ID of the class without spaces found on UCSB Curriculum Search.
- - - - - - - - - - - - - - - - - - - - - - - - - - - -
*The Instructor*
is the exact name of the Instructor of the class found on UCSB Curriculum Search.
If the class Instructor is TBA, the Instructor is given a default value of ‘TBA’.
If the Instructor is given a default value of ‘TBA’, the Instructor’s Rating automatically receives a default value of ‘D’.
- - - - - - - - - - - - - - - - - - - - - - - - - - - -
*The Instructor’s Rating*
is the average rating of the instructor found on RateMyProfessors.
If the instructor does not have a rating on RateMyProfessors, the Instructor’s Rating is given a default value of ‘D’.
As of 4/25/17, the default value ‘D’ is equivalent to 3.
- - - - - - - - - - - - - - - - - - - - - - - - - - - -
For example,

ANTH 3 is taught by SMITH S T

SMITH S T has an average rating of 2.5

The following line will be added to anthropology_ratings.txt for this class. . .

ANTH3,SMITH S T,2.5
- - - - - - - - - - - - - - - - - - - - - - - - - - - - 
For example,

CH ST 199 is taught by TBA

TBA has an average rating of D

The following line will be added to chicano_studies_ratings.txt for this class. . .

CHST199,TBA,D
- - - - - - - - - - - - - - - - - - - - - - - - - - - - 
A note about formatting

I found it necessary to include the instructor’s name in the class ratings files because it will allow us to submit real-time updates to class ratings files.
For example, if an instructor’s rating fluctuates due to new ratings submitted to RateMyProfessors, we can simply adjust the instructor’s rating found in the professor_ratings.txt file.
Once we adjust the instructor’s rating in the professor_ratings.txt file, we can run a simple program that will rewrite the class ratings files to include updated instructor ratings.

By including the instructor’s name with each class in the class ratings files, we can identify how the existing rating was derived.
This allows us to reuse class ratings files throughout different quarters and years.
We only adjust ratings if an instructor has an updated RateMyProfessors rating, or if a completely new instructor teaches the class.
- - - - - - - - - - - - - - - - - - - - - - - - - - - -


- - - - - - - - - - - - - - - - - - - - - - - - - - - -
HOW TO READ THE PROFESSOR RATINGS FILE

Each line of the professor ratings file has two pieces of data separated by commas.

The Instructor, and Instructor’s Rating
- - - - - - - - - - - - - - - - - - - - - - - - - - - -
*The Instructor*
is the exact name of the Instructor found on UCSB Curriculum Search.
- - - - - - - - - - - - - - - - - - - - - - - - - - - -
*The Instructor’s Rating*
is the average rating of the instructor found on RateMyProfessors.
If the instructor does not have a rating on RateMyProfessors, the Instructor’s Rating is given a default value of ‘D’.
As of 4/25/17, the default value ‘D’ is equivalent to 3.
- - - - - - - - - - - - - - - - - - - - - - - - - - - -
For example,

COSTANZO C has an average rating of 3.7

The following line will be added to professor_ratings.txt. . .

COSTANZO C,3.7
- - - - - - - - - - - - - - - - - - - - - - - - - - - - 
For example,

GIBBS J L has an average rating of D

The following line will be added to professor_ratings.txt. . .

GIBBS J L,D
- - - - - - - - - - - - - - - - - - - - - - - - - - - - 
