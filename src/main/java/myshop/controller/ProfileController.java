package myshop.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.inject.Inject;

import myshop.domain.CompareOptions;
import myshop.domain.DataPoint;
import myshop.domain.DataPointView;
import myshop.domain.DateRange;
import myshop.domain.DirectionOptions;
import myshop.domain.RangeOptions;
import myshop.domain.StoreAndProduct;
import myshop.domain.TimeScale;
import myshop.helper.DataPointHelper;
import myshop.helper.DatesUtil;
import myshop.helper.TimeScaleLabelTranslator;
import myshop.service.DataPointService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {
	private static final Logger Log = LoggerFactory.getLogger(ProfileController.class);

	private final DataPointService profileBuilderService;

	@Inject
	public ProfileController(DataPointService profileBuilderService) {
		this.profileBuilderService = profileBuilderService;
	}

	@RequestMapping(value = "/product/profile/{lineNumber}", method = RequestMethod.GET)
	public List<DataPointView> getProfile(@PathVariable int lineNumber,
			@RequestParam int storeNumber,
			@RequestParam String dateFrom,
			@RequestParam RangeOptions dateTo,
			@RequestParam(defaultValue = "HOUR") TimeScale profileScale) throws ParseException {

		DateRange dates = DatesUtil.validateAndGetDateRange(dateFrom, dateTo);
		StoreAndProduct storeAndProduct = new StoreAndProduct(lineNumber, storeNumber);
		Log.info(
				"Starting to process request for profile data: lineNumber {}, store {}, scale {}, range {}, from {}, to {}",
				lineNumber, storeNumber, profileScale, dateTo, dates.getStart(), dates.getEnd());

		List<DataPoint> dataPoints = profileBuilderService.buildProfile(storeAndProduct, dates, profileScale);

		List<DataPointView> views = DataPointHelper.convertToDataPointView(dataPoints, profileScale);

		if (views.isEmpty()) {
			Log.info("No data found!");
		}

		Log.info("Completed  profile request.");

		return views;
	}

	@RequestMapping(value = "/product/profileToCompare/{lineNumber}", method = RequestMethod.GET)
	public List<DataPointView> getProfileToCompare(	@PathVariable int lineNumber,
			@RequestParam int storeNumber,
			@RequestParam String dateFrom,
			@RequestParam RangeOptions dateTo,
			@RequestParam(defaultValue = "HOUR") TimeScale profileScale,
			@RequestParam CompareOptions compare) throws ParseException {

		StoreAndProduct storeAndProduct = new StoreAndProduct(lineNumber, storeNumber);
		DateRange dateRange = DatesUtil.validateAndGetDateRange(dateFrom, dateTo);
		String day = TimeScaleLabelTranslator.getLabelFromDate(dateRange.getStart());

		Log.info(
				"Starting to process  request for comparison profile data: lineNumber {}, store {}, scale {}, week day {}, comparison {}",
				lineNumber, storeNumber, profileScale, day, compare);

		List<DataPoint> dataPoints;

		if (compare.forAverage()) {
			dataPoints = profileBuilderService.buildProfileFromAggregation(storeAndProduct, profileScale, compare.forAverageDay(), day);
		} else {
			DateRange comparisonDateRange = DatesUtil.findComparisonDate(compare, dateRange, profileScale, dateTo);
			Log.info("Comparison profile will be built for date range {} ", comparisonDateRange);
			dataPoints = profileBuilderService.buildProfile(storeAndProduct, comparisonDateRange, profileScale);
		}

		List<DataPointView> views = DataPointHelper.convertToDataPointView(dataPoints, profileScale);

		Log.info("Completed comparison profile request.");

		return views;

	}

	@RequestMapping(value = "/profile/findShiftedDate", method = RequestMethod.GET)
	public String findShiftedDateForProfileLabel(	@RequestParam String dateFrom,
			@RequestParam RangeOptions dateTo,
			@RequestParam DirectionOptions direction) throws ParseException {
		return DatesUtil.validateAndShiftDate(dateFrom, dateTo, direction);
	}

	@RequestMapping(value = "/profile/findEndDate", method = RequestMethod.GET)
	public String findStartAndEndDatesForProfileLabel(	@RequestParam String dateFrom,
			@RequestParam RangeOptions dateTo) throws ParseException {
		DateRange dates = DatesUtil.validateAndGetDateRange(dateFrom, dateTo);
		return DatesUtil.getDatesAsString(dates);
	}

	@RequestMapping(value = "/profile/findComparisonDates", method = RequestMethod.GET)
	public String findComparisonStartAndEndDatesForProfileLabel(	@RequestParam String dateFrom,
			@RequestParam RangeOptions dateTo,
			@RequestParam(defaultValue = "HOUR") TimeScale profileScale,
			@RequestParam CompareOptions compare) throws ParseException {

		DateRange dateRange = DatesUtil.validateAndGetDateRange(dateFrom, dateTo);
		String day = TimeScaleLabelTranslator.getLabelFromDate(dateRange.getStart());
		if (compare.forAverage()) {
			return compare.forAverageDay() ? "Average " + day : "Average";
		} else {
			DateRange comparisonDateRange = DatesUtil.findComparisonDate(compare, dateRange, profileScale, dateTo);
			return DatesUtil.getDatesAsString(comparisonDateRange);
		}
	}

}
