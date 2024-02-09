
PixabayDemoApp is an Android application enabling users to search and browse royalty-free stock
images. Leveraging the [Pixabay API](https://pixabay.com/), it's written in Kotlin, employs
Jetpack Compose, and adheres to Clean Architecture principles.

# Key features:
- Explore images via keyword search
- Access detailed image information, including likes, comments, and downloads
- Supports Dark theme
- Seamlessly adjusts between portrait and landscape orientations

# Potential Improvements:
- Implement pagination for smoother browsing
- Enhance test coverage for robustness

# Comments:
- In the absence of specific caching requirements, I introduced a flexible interface with an
  implementation that utilizes RAM to cache the latest image request results. While caching
  strategies may vary greatly depending on factors such as the type of data to be cached,
  persistence mechanisms, and caching strategies, this approach offers adaptability and efficiency.