import sys
import re
# The following source was used for the projectL
# [1] Python Contributors. "Regular expression operations." docs.python.org.
#     Available: https://docs.python.org/3/library/re.html

def main():
    args = sys.argv[1:]
    output = []

    for file_name in args:
        f = open(file_name, "r")
        pattern = re.compile(file_name)
        lines = f.readlines()

        for l in lines:
            if pattern.match(l) is not None:
                output.append(file_name)
                break
    return output

if __name__ == "__main__":
    main()