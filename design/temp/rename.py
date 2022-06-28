from pathlib import Path

for png in sorted(list(Path(input('> ')).glob('*.*'))):
    png.rename(Path(png).parent.joinpath(Path(png).name.replace(str(Path(png).stem[:2]), (str(int(Path(png).stem[:2]) + 4).zfill(2)))))
