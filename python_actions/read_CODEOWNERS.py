import sys
import re
import json
# The following source was used for the projectL
# [1] Python Contributors. "Regular expression operations." docs.python.org.
#     Available: https://docs.python.org/3/library/re.html
#
# [2] GeekForGeek Contributors. "How To Convert Python Dictionary To JSON?"
#     GeeksForGeeks.org. Available: https://www.geeksforgeeks.org/how-to-convert-python-dictionary-to-json/

def main():
    args = sys.argv[1:]
    output = {"files": []}
    r = open(".github/CODEOWNERS", "r")

    for file_name in args:
        pattern = re.compile(file_name)
        lines = r.readlines()

        for l in lines:
            if pattern.match(l) is not None:
                output["files"].append(file_name)
                break
     
    r.close()
    return json.dumps(output, indent = 4) 

if __name__ == "__main__":
    main()