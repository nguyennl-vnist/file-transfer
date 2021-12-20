import * as React from 'react';
import PropTypes from 'prop-types';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import { Grid } from '@material-ui/core';
import PdfToTxt from './components/pdfToTxt';

function TabPanel(props) {
    const { children, value, index, ...other } = props;

    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`simple-tabpanel-${index}`}
            aria-labelledby={`simple-tab-${index}`}
            {...other}
        >
            {value === index && (
                <Box sx={{ p: 3 }}>
                    <Typography>{children}</Typography>
                </Box>
            )}
        </div>
    );
}

TabPanel.propTypes = {
    children: PropTypes.node,
    index: PropTypes.number.isRequired,
    value: PropTypes.number.isRequired,
};

function a11yProps(index) {
    return {
        id: `simple-tab-${index}`,
        'aria-controls': `simple-tabpanel-${index}`,
    };
}

export default function BasicTabs() {
    const [value, setValue] = React.useState(0);

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };


    return (
        <React.Fragment>
            <Grid container>
                <Grid item xs={2}></Grid>
                <Grid item xs={8}>
                    <Box sx={{ width: '100%', justifyContent: "center", textAlign: "center", alignItems: "center" }}>
                        <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
                            <Tabs value={value} onChange={handleChange} aria-label="basic tabs example">
                                <Tab label="PDF -> TXT" {...a11yProps(0)} />
                                <Tab label="PDF -> IMAGE" {...a11yProps(1)} />
                                <Tab label="PDF -> DOCX" {...a11yProps(2)} />
                                <Tab label="TXT -> PDF" {...a11yProps(3)} />
                                <Tab label="IMAGE -> PDF" {...a11yProps(4)} />
                                <Tab label="ZIP FILE(S)/ FOLDER" {...a11yProps(5)} />
                            </Tabs>
                        </Box>
                        <TabPanel value={value} index={0}>
                            <PdfToTxt />
                        </TabPanel>
                        <TabPanel value={value} index={1}>
                            Item Two
                        </TabPanel>
                        <TabPanel value={value} index={2}>
                            Item Three
                        </TabPanel>
                        <TabPanel value={value} index={3}>
                            Item One
                        </TabPanel>
                        <TabPanel value={value} index={4}>
                            Item Two
                        </TabPanel>
                        <TabPanel value={value} index={5}>
                            Item Three
                        </TabPanel>
                    </Box>
                </Grid>
                <Grid item xs={2}></Grid>
            </Grid>

        </React.Fragment>

    );
}
