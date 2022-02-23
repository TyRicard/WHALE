import sys
import re
# The following source was used for the projectL
# [1] Python Contributors. "Regular expression operations." docs.python.org.
#     Available: https://docs.python.org/3/library/re.html

def main():
    args = sys.argv[1:]
    output = []
    r = open(".github/CODEOWNERS", "r")
    x = open(".github/temp_file.txt", "x")
    w = open(".github/temp_file.txt", "w")

    for file_name in args:
        pattern = re.compile(file_name)
        lines = r.readlines()

        for l in lines:
            if pattern.match(l) is not None:
                output.append(file_name)
                break

    w.write(output)           
    r.close()
    w.close()
    return

if __name__ == "__main__":
    main()