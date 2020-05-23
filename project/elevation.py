from pathlib import Path

DATA_PATH = Path(__file__).resolve().parent / "data"


class ElevationLoader:
    def __init__(self):
        self.data = self.load()

    def load(self):
        data = {}
        with open(DATA_PATH / "heightmap.xyz") as fp:
            line = fp.readline()
            while line:
                parts = line.split()
                line = fp.readline()
                x = int(parts[0].split('.')[0])
                y = int(parts[1].split('.')[0])
                z = int(parts[2].split('.')[0])
                if x not in data:
                    data[x] = {}
                data[x][y] = z
        return data

    def get_height(self, x, y):
        x_dict = self.data.get(x, self.data[min(self.data.keys(), key=lambda k: abs(k-x))])
        return x_dict.get(y, x_dict[min(x_dict.keys(), key=lambda k: abs(k-y))])

