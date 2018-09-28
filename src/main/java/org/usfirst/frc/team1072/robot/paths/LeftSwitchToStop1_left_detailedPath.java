package org.usfirst.frc.team1072.robot.paths;

public class LeftSwitchToStop1_left_detailedPath extends Path {

	static {
		Path.paths.put("LeftSwitchToStop1_left_detailed", new LeftSwitchToStop1_left_detailedPath());
	}

	public LeftSwitchToStop1_left_detailedPath() {
		super(new double[][] {
			{ -0.0, 0.0 },
			{ -2.81E-4, 0.0 },
			{ -0.001653, -2.9999999995311555E-6 },
			{ -0.004645, -2.0000000000131024E-5 },
			{ -0.009257, -6.600000000034356E-5 },
			{ -0.015489, -1.5900000000002024E-4 },
			{ -0.02334, -3.1300000000022976E-4 },
			{ -0.032812, -5.470000000000752E-4 },
			{ -0.043905, -8.749999999997371E-4 },
			{ -0.056619, -0.0013139999999998153 },
			{ -0.070955, -0.0018799999999998818 },
			{ -0.086914, -0.002589999999999648 },
			{ -0.104497, -0.003459000000000323 },
			{ -0.123706, -0.0045039999999998415 },
			{ -0.144543, -0.005740999999999552 },
			{ -0.167008, -0.007185999999999915 },
			{ -0.191105, -0.008855999999999753 },
			{ -0.216837, -0.010767999999999667 },
			{ -0.244205, -0.012935999999999837 },
			{ -0.273214, -0.015378000000000114 },
			{ -0.303868, -0.01811000000000007 },
			{ -0.33617, -0.021149000000000306 },
			{ -0.370125, -0.024510000000000254 },
			{ -0.405738, -0.028211999999999904 },
			{ -0.443014, -0.032269000000000325 },
			{ -0.481959, -0.03669899999999959 },
			{ -0.522581, -0.041519000000000084 },
			{ -0.564884, -0.04674499999999959 },
			{ -0.608878, -0.05239299999999947 },
			{ -0.654568, -0.0584819999999997 },
			{ -0.701964, -0.06502799999999986 },
			{ -0.751074, -0.07204799999999967 },
			{ -0.801907, -0.07955799999999957 },
			{ -0.854473, -0.08757699999999957 },
			{ -0.90878, -0.09612200000000026 },
			{ -0.964839, -0.10520999999999958 },
			{ -1.022659, -0.1148579999999999 },
			{ -1.082253, -0.12508500000000033 },
			{ -1.143629, -0.13590699999999956 },
			{ -1.206798, -0.14734399999999948 },
			{ -1.271771, -0.15941199999999967 },
			{ -1.338558, -0.17212899999999998 },
			{ -1.407168, -0.18551499999999965 },
			{ -1.477612, -0.19958700000000018 },
			{ -1.549898, -0.21436299999999964 },
			{ -1.624034, -0.2298619999999998 },
			{ -1.700027, -0.2461019999999996 },
			{ -1.777883, -0.26310199999999995 },
			{ -1.857605, -0.28088099999999994 },
			{ -1.939196, -0.2994570000000003 },
			{ -2.022656, -0.31884900000000016 },
			{ -2.107983, -0.3390759999999995 },
			{ -2.195171, -0.3601559999999999 },
			{ -2.284213, -0.38210699999999953 },
			{ -2.375094, -0.4049490000000002 },
			{ -2.467799, -0.4287000000000001 },
			{ -2.562306, -0.45337799999999984 },
			{ -2.658297, -0.47900100000000023 },
			{ -2.7549, -0.5055839999999998 },
			{ -2.851522, -0.5331330000000003 },
			{ -2.948112, -0.5616490000000001 },
			{ -3.044611, -0.5911299999999997 },
			{ -3.140955, -0.6215760000000001 },
			{ -3.237071, -0.6529850000000001 },
			{ -3.332882, -0.6853559999999996 },
			{ -3.428303, -0.7186849999999998 },
			{ -3.52324, -0.7529680000000001 },
			{ -3.617596, -0.7881999999999998 },
			{ -3.711264, -0.824376 },
			{ -3.804132, -0.8614889999999997 },
			{ -3.896082, -0.8995299999999995 },
			{ -3.986991, -0.938491 },
			{ -4.07673, -0.9783609999999996 },
			{ -4.165166, -1.0191280000000003 },
			{ -4.252162, -1.0607800000000003 },
			{ -4.337581, -1.1033020000000002 },
			{ -4.421281, -1.1466769999999995 },
			{ -4.503124, -1.1908899999999996 },
			{ -4.58297, -1.2359209999999998 },
			{ -4.660682, -1.2817509999999999 },
			{ -4.736129, -1.3283579999999997 },
			{ -4.808897, -1.3757189999999997 },
			{ -4.878059, -1.4238079999999997 },
			{ -4.942993, -1.4725889999999997 },
			{ -5.003636, -1.5220189999999998 },
			{ -5.059937, -1.5720549999999998 },
			{ -5.111865, -1.6226539999999998 },
			{ -5.159407, -1.6737729999999997 },
			{ -5.202568, -1.7253669999999999 },
			{ -5.241369, -1.777393 },
			{ -5.275852, -1.8298069999999997 },
			{ -5.306073, -1.882565 },
			{ -5.332107, -1.935626 },
			{ -5.354041, -1.988947 },
			{ -5.371979, -2.042487 },
			{ -5.386034, -2.0962069999999997 },
			{ -5.39633, -2.150067 },
			{ -5.403, -2.204031 },
			{ -5.406184, -2.258061 },
			{ -5.406027, -2.3121229999999997 },
			{ -5.402675, -2.366183 },
			{ -5.396278, -2.42021 },
			{ -5.386984, -2.474172 },
			{ -5.37494, -2.528042 },
			{ -5.360289, -2.581792 },
			{ -5.34317, -2.635395 },
			{ -5.323716, -2.6888259999999997 },
			{ -5.302056, -2.742063 },
			{ -5.278309, -2.795084 },
			{ -5.252587, -2.847867 },
			{ -5.224996, -2.9003929999999998 },
			{ -5.195632, -2.9526429999999997 },
			{ -5.164582, -3.004599 },
			{ -5.131926, -3.056245 },
			{ -5.097734, -3.107564 },
			{ -5.062069, -3.1585419999999997 },
			{ -5.024986, -3.209162 },
			{ -4.986531, -3.259412 },
			{ -4.946743, -3.309277 },
			{ -4.905655, -3.358745 },
			{ -4.863292, -3.407801 },
			{ -4.819673, -3.456434 },
			{ -4.774813, -3.504631 },
			{ -4.728718, -3.552379 },
			{ -4.681393, -3.599666 },
			{ -4.632837, -3.64648 },
			{ -4.583045, -3.692809 },
			{ -4.532009, -3.738639 },
			{ -4.479719, -3.783959 },
			{ -4.426161, -3.828756 },
			{ -4.118412, -3.873018 },
			{ -3.628381, -3.914202 },
			{ -3.58858, -3.9504859999999997 },
			{ -3.546573, -3.986372 },
			{ -3.502323, -4.021838 },
			{ -3.455789, -4.056861 },
			{ -3.406936, -4.091419 },
			{ -3.355483, -4.125488 },
			{ -3.301414, -4.159043 },
			{ -3.244951, -4.192057 },
			{ -3.186069, -4.224506 },
			{ -3.124749, -4.256367 },
			{ -3.060979, -4.287615 },
			{ -2.994752, -4.318224 },
			{ -2.926071, -4.348172 },
			{ -2.85495, -4.377433 },
			{ -2.781413, -4.405982 },
			{ -2.705498, -4.433796 },
			{ -2.627259, -4.460851 },
			{ -2.546766, -4.487124 },
			{ -2.464107, -4.512592 },
			{ -2.379395, -4.537233 },
			{ -2.292763, -4.561027 },
			{ -2.204368, -4.583954 },
			{ -2.114399, -4.605998 },
			{ -2.02307, -4.627142 },
			{ -1.930625, -4.647373 },
			{ -1.837342, -4.666679 },
			{ -1.743528, -4.685052 },
			{ -1.649523, -4.702487 },
			{ -1.555697, -4.718983 },
			{ -1.462449, -4.73454 },
			{ -1.370203, -4.7491639999999995 },
			{ -1.279407, -4.762866 },
			{ -1.190522, -4.77566 },
			{ -1.104024, -4.787566 },
			{ -1.020389, -4.7986059999999995 },
			{ -0.940088, -4.80881 },
			{ -0.863581, -4.818211 },
			{ -0.791301, -4.826846 },
			{ -0.723648, -4.834759 },
			{ -0.660981, -4.841996 },
			{ -0.603606, -4.848606 },
			{ -0.551767, -4.854642 },
			{ -0.505645, -4.8601589999999995 },
			{ -0.465346, -4.865216 },
			{ -0.430904, -4.869869 },
			{ -0.402277, -4.874178 },
			{ -0.379346, -4.878201 },
			{ -0.361927, -4.881995 },
			{ -0.349764, -4.885614 },
			{ -0.342548, -4.889111 },
			{ -0.339918, -4.892537 },
			{ -0.34147, -4.895936 },
			{ -0.346772, -4.899351 },
			{ -0.355368, -4.902819 },
			{ -0.36679, -4.906372 },
			{ -0.38057, -4.9100399999999995 },
			{ -0.39624, -4.9138459999999995 },
			{ -0.413349, -4.917808 },
			{ -0.431461, -4.921942 },
			{ -0.450165, -4.9262559999999995 },
			{ -0.469075, -4.930758 },
			{ -0.487923, -4.935449 },
			{ -0.5065, -4.940328 },
			{ -0.524554, -4.945393 },
			{ -0.541867, -4.950638 },
			{ -0.558255, -4.9560569999999995 },
			{ -0.573559, -4.96164 },
			{ -0.58765, -4.967375 },
			{ -0.600421, -4.973252 },
			{ -0.61179, -4.9792559999999995 },
			{ -0.621695, -4.985374 },
			{ -0.630088, -4.991591 },
			{ -0.636942, -4.997892 },
			{ -0.64224, -5.004261 },
			{ -0.645978, -5.0106839999999995 },
			{ -0.648164, -5.017143 },
			{ -0.648812, -5.023625 },
			{ -0.647946, -5.030113 },
			{ -0.645594, -5.036593 },
			{ -0.641791, -5.043048 },
			{ -0.636575, -5.049466 },
			{ -0.629987, -5.055832 },
			{ -0.622072, -5.062132 },
			{ -0.612876, -5.068353 },
			{ -0.602446, -5.074482 },
			{ -0.591029, -5.080506 },
			{ -0.578886, -5.086416 },
			{ -0.566089, -5.092205 },
			{ -0.552708, -5.097866 },
			{ -0.53881, -5.103393 },
			{ -0.524458, -5.108781 },
			{ -0.509715, -5.114026 },
			{ -0.494639, -5.119123 },
			{ -0.479286, -5.1240689999999995 },
			{ -0.463708, -5.128862 },
			{ -0.447956, -5.133499 },
			{ -0.432077, -5.137979 },
			{ -0.416117, -5.1423 },
			{ -0.400119, -5.1464609999999995 },
			{ -0.384122, -5.150462 },
			{ -0.368164, -5.154303 },
			{ -0.352282, -5.157985 },
			{ -0.336508, -5.1615079999999995 },
			{ -0.320875, -5.164873 },
			{ -0.305412, -5.168081 },
			{ -0.290147, -5.171136 },
			{ -0.275106, -5.174037 },
			{ -0.260314, -5.176788 },
			{ -0.245792, -5.179391 },
			{ -0.231562, -5.181849 },
			{ -0.217645, -5.184165 },
			{ -0.204058, -5.186341 },
			{ -0.190818, -5.188382 },
			{ -0.177941, -5.19029 },
			{ -0.165443, -5.192069 },
			{ -0.153335, -5.193724 },
			{ -0.141631, -5.195257 },
			{ -0.130342, -5.196673 },
			{ -0.119479, -5.197977 },
			{ -0.10905, -5.199172 },
			{ -0.099065, -5.2002619999999995 },
			{ -0.089531, -5.201253 },
			{ -0.080456, -5.202148 },
			{ -0.071845, -5.202953 },
			{ -0.063704, -5.203671 },
			{ -0.056039, -5.204308 },
			{ -0.048854, -5.2048689999999995 },
			{ -0.042152, -5.205357 },
			{ -0.035937, -5.205779 },
			{ -0.030211, -5.206138 },
			{ -0.024978, -5.20644 },
			{ -0.020239, -5.20669 },
			{ -0.015995, -5.206892 },
			{ -0.012249, -5.207052 },
			{ -0.009001, -5.207175 },
			{ -0.006251, -5.207265 },
			{ -0.004001, -5.207327 },
			{ -6.04E-4, -5.207367 },
			{ -9.76E-4, -5.207373 },
			{ -9.88E-4, -5.207383 }
		});
	}
}