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
    files = files[1:]
    print(files)
    r = open("/CI_CODEOWNERS", "r")

    for file_name in files:
        pattern = re.compile(file_name)
        lines = r.readlines()

        for l in lines:
            if pattern.match(l) is not None:
                output.append(file_name)
                break
     
    r.close()

    num_files = len(output)
    iterator = 0
    if num_files == 0:
        ret_str = ""
    else:
        ret_str = "The following files are to be removed from CI_CODEOWNERS (CI workflow file) and CODEOWNERS: "
        for file_name in output:
            if iterator == num_files - 1:
                ret_str = ret_str + file_name + "."
            else:
                ret_str = ret_str + file_name + ", "
            iterator = iterator + 1

    print("::set-output name=deletion_list::" + ret_str)
    print(ret_str)
    return ret_str

if __name__ == "__main__":
    main()