
# Architectural Overview

 Model-View-Presenter (MVP) is a user interface architectural pattern engineered to facilitate automated unit testing and improve the separation of concerns in presentation logic:

* The model is an interface defining the data to be displayed or otherwise acted upon in the user interface.
* The view is a passive interface that displays data (the model) and routes user commands (events) to the presenter to act upon that data.
* The presenter acts upon the model and the view. It retrieves data from repositories (the model), and formats it for display in the view.

# How it Works:

1. User performs a gesture on the `View`
2. `View` delegates the user command to its `Presenter`
3. `Presenter` performs operation and/or retrieves data from the repository (i.e. SQLite database or web service)
4. `Presenter` passes data results back to the `View` through its defined interface
5. `View` populates its subviews using repository data