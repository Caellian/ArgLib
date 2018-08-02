# ArgLib v1.1.0
[![Build Status](https://travis-ci.org/Caellian/ArgLib.svg?branch=master)](https://travis-ci.org/Caellian/ArgLib)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/fb0f64eceb8f42b8bfb38771991e05d9)](https://www.codacy.com/project/Caellian/ArgLib/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Caellian/ArgLib&amp;utm_campaign=Badge_Grade_Dashboard)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
## Simple argument parsing library.

### Using ArgLib
1. Add jitpack.io repo in your root build.gradle at the end of repositories:
```groovy
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

2. Add the dependency:
```groovy
	dependencies {
	        compile 'com.github.caellian:arglib:1.1.+'
	}
```

If you want to learn how to use ArgLib or have problems using it, you can consult the [ArgLib Wiki](https://github.com/Caellian/ArgLib/wiki/ArgLib-Wiki).

### Building
ArgLib uses Gradle as it's build & dependency management system. All you have to do to build the project is download
the repository locally use ```.\gradlew jar``` on Linux or ```./gradlew.bat jar``` on Windows.

Your built files should be then available in 'build\libs' folder.

If you come across any errors during building of files please report them on project [GitHub Issues](https://github.com/Caellian/ArgLib/issues) page.

### Contribution
Source of ArgLib is available on [GitHub](https://github.com/Caellian/ArgLib).

Anyone can contribute to ArgLib in accordance with contribution rules stated
in [CONTRIBUTION.md](https://github.com/Caellian/ArgLib/blob/master/CONTRIBUTING.md) file in Stellar GitHub repository.

### License
ArgLib, Simple configurable argument parsing library written in Kotlin.
Copyright (C) 2018 Tin Svagelj (a.k.a. Caellian) <tin.svagelj.email@gmail.com>

Permission is hereby granted, free of charge, to any person obtaining a
copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included
in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.