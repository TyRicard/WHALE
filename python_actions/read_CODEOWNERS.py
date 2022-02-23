import sys
import re
import os
# The following source was used for the projectL
# [1] Python Contributors. "Regular expression operations." docs.python.org.
#     Available: https://docs.python.org/3/library/re.html
# 
# [2] https://able.bio/rhett/how-to-set-and-get-environment-variables-in-python--274rgt5
#
# [3] https://docs.github.com/en/actions/creating-actions/creating-a-docker-container-action

def main():
    output = []
    file_str = os.getenv('INPUT_FILES')
    files = file_str.split('|')

    r = open(".github/CODEOWNERS", "r")

    for file_name in files:
        pattern = re.compile(file_name)
        lines = r.readlines()

        for l in lines:
            if pattern.match(l) is not None:
                output.append(file_name)
                break
     
    r.close()

    ret_str = "The required strings are the following: \n"
    for file_name in output:
        ret_str = ret_str + file_name + "\n"

    print("::set-output name=deletion_list::" + ret_str)
    return ret_str

if __name__ == "__main__":
    main()