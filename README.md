Just prototyping performance of hunspell before we build a full blone
API for SBK.

Notes to set up from memory:
- git clone https://github.com/mweimerskirch/AndroidHunspellService.git
- Build the native libraries according to their instructions with
  ndk-build.
- Add this project to eclipse
- Add the hunspell service to eclipse as well and make sure it's
properties in Eclipse set it to be a "library project".
- Add that project as a reference to this project under properties in
eclipse for this proto type project.
- You'll probably need to add the libs/support-v4.jar to your build path
in Eclipse right click this proto type project and select build path ->
add external archives and add the libs/ jar file.
- Hopefully I haven't forgotten anything and it should build.

See the OnCreate method in
src/com/example/spelling/SpellingService.java it points to a specific
bdic file. You might need to change the filename from en_au.bdic to
something more appropriate.
And finally: for whatever filename you change it to make sure that file
exists in the root directory of your external storage like sd card for
example.

en_au.bdic included here for convenience.

Get your dictionaries here:
git clone https://chromium.googlesource.com/chromium/deps/hunspell_dictionaries.git
